import java.util.*;

public class InstagramCluster {
    Map<String, List<String>> graph;

    public InstagramCluster(Map<String, List<String>> graph) {
        orderGraph(graph);
    }

    private void orderGraph(Map<String, List<String>> graph) {
        TreeMap<String, List<String>> sortedGraph = new TreeMap<>(graph); // Usando TreeMap pois já ordena o key set
        // Ordenando vizinhos de cada vertice
        for (String key : sortedGraph.keySet()) {
            List<String> neighbors = sortedGraph.get(key);
            Collections.sort(neighbors);
            sortedGraph.put(key, neighbors);
        }
        this.graph = sortedGraph;
    }

    /**
     * Primeiro DFS, retorna a lista na ordem de vertices visitados.
     */
    public Set<String> dfs() {
        // Gera estruturas de dados para finalizados e visitados.
        LinkedList<String> finished = new LinkedList<>();
        Set<String> visited = new LinkedHashSet<>();
        for (String node : graph.keySet()) { // Itera cada vertice no grafo
            if (!visited.contains(node)) { // Verifica se vertice já foi visitado.
                dfsVisit(node, visited, finished); // Realiza dfs no vertice visitado.
            }
        }
        // Após toda iteração e recursividade, retorna a lista na ordem dos que foram visitados.
        return visited;
    }

    /**
     * DFS Para os vertices que estão sendo visitados.
     */
    private void dfsVisit(String node, Set<String> visited, LinkedList<String> finished) {
        visited.add(node); // Adiciona vertice na lista de visitados.
        System.out.println("Visitados ->> " + visited);
        for (String neighbor : graph.get(node)) { // Itera cada vizinho do vertice visitado. Indo mais fundo possivel
            if (!visited.contains(neighbor)) { // Verifica se vizinho já foi marcado como visitado
                dfsVisit(neighbor, visited, finished); // Faz recursividade para marcar a maior quantidade de vertices possiveis como visitados.
            }
        }
        finished.addFirst(node); // Coloca vertice depois de iterado para dentro da lista de vertices finalizados.
        System.out.println("Finalizados ->> " + finished);
    }

    /**
     *  Retora a transposta do grafo.
     */
    public Map<String, List<String>> getTranspose() {
        Map<String, List<String>> transpose = new HashMap<>(); // Inicializa variavel de transposta.
        for (String node : graph.keySet()) {
            transpose.put(node, new ArrayList<>()); // Adiciona os vertices do grafo, sem vizinhos, para a transposta
        }
        for (String node : graph.keySet()) { // Itera cada vertice do grafo.
            for (String neighbor : graph.get(node)) { // Itera cada vizinho do vertice iterado
                transpose.get(neighbor).add(node); // Faz adição inversa de vizinho no grafo, assim gerando a transposta aos poucos.
            }
        }
        return transpose; // Retorno da transposta completa.
    }


    /**
     * Realiza DFS especifíca da transposta e retornando o resultado com os componentes finais.
     */
    public List<List<String>> dfsTranspose(Map<String, List<String>> transpose, Set<String> sortedNodes) {
        List<List<String>> result = new ArrayList<>();
        Set<String> visited = new LinkedHashSet<>();
        for (String node : sortedNodes) {  // Iteração do dfs, seguindo agora a ordem gerada pelo dfs do grafo padrão.
            if (!visited.contains(node)) { // Mesmo processo, verificando se vertice já foi marcado como visitado.
                List<String> component = new ArrayList<>(); // Inicializa componente
                dfsTransposeVisit(node, transpose, visited, component); // Realiza dfs no vertice.
                result.add(component); // Adiciona o componente gerado a partir do dfs no resultado final.
                System.out.println("Componente da transposta ->> " + component);
            }
        }
        return result; // Retorna resultado final.
    }

    /**
     * DFS Para cada vertice da transposta.
     */
    private void dfsTransposeVisit(String node, Map<String, List<String>> transpose, Set<String> visited, List<String> component) {
        visited.add(node); // Marca o vertice como visitado.
        System.out.println("Visitados da transposta ->> " + visited);
        component.add(node); // Adiciona o vertice no componente.
        for (String neighbor : transpose.get(node)) { // Itera os vizinhos do vertice.
            if (!visited.contains(neighbor)) { // Verifica se vizinho ja foi marcado como visitado.
                dfsTransposeVisit(neighbor, transpose, visited, component); // Realiza a recursividade para buscar todos os vizinhos que form o componente.
            }
        }
    }

    public void printGraph() {
        for (String user : graph.keySet()) {
            System.out.print(user + ": ");
            for (String friend : graph.get(user)) {
                System.out.print(friend + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Map<String, List<String>> graph = new HashMap<>();
        // Exemplo 01
//        graph.put("a", Arrays.asList("d", "c", "e"));
//        graph.put("b", Collections.singletonList("e"));
//        graph.put("c", Arrays.asList("f", "g"));
//        graph.put("d", Arrays.asList("b", "c", "e"));
//        graph.put("e", Collections.singletonList("g"));
//        graph.put("f", Arrays.asList("d", "g"));
//        graph.put("g", Collections.singletonList("b"));

        // Exemplo 02
        graph.put("a", Arrays.asList("d", "b"));
        graph.put("b", Arrays.asList("d", "e"));
        graph.put("c", Collections.singletonList("a"));
        graph.put("d", Collections.singletonList("c"));
        graph.put("e", Collections.singletonList("f"));
        graph.put("f", Collections.singletonList("g"));
        graph.put("g", Collections.singletonList("e"));

        InstagramCluster instagram = new InstagramCluster(graph);

        Map<String, List<String>> thisGraph = instagram.graph;
        Map<String, List<String>> thisGraphTranspose = instagram.getTranspose();
        System.out.println("Grafo padrão ->> " + thisGraph);
        System.out.println("Grafo transposto ->> " + thisGraphTranspose);

        System.out.println("----- Passo a passo do dfs normal -----");
        Set<String> dfs = instagram.dfs();
        System.out.println("---------------------------------------");

        System.out.println("Ordem de Visitados no DFS com grafo padrão ->> " + dfs);

        System.out.println("----- Passo a passo do dfs da tranposta -----");
        List<List<String>> dfsTransposta = instagram.dfsTranspose(thisGraphTranspose, dfs);
        System.out.println("---------------------------------------------");
        System.out.println("Componentes gerados no DFS do grafo transposto na ordem do primeiro dfs ->> " + dfsTransposta);

    }
}