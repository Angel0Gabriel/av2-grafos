package NotCommented;

import java.util.*;

public class InstagramCluster {
    Map<String, List<String>> graph;

    public InstagramCluster(Map<String, List<String>> graph) {
        orderGraph(graph);
    }

    private void orderGraph(Map<String, List<String>> graph) {
        TreeMap<String, List<String>> sortedGraph = new TreeMap<>(graph);
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
        LinkedList<String> finished = new LinkedList<>();
        Set<String> visited = new LinkedHashSet<>();
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                dfsVisit(node, visited, finished);
            }
        }
        return visited;
    }

    /**
     * DFS Para os vertices que estão sendo visitados.
     */
    private void dfsVisit(String node, Set<String> visited, LinkedList<String> finished) {
        visited.add(node);
        System.out.println("Visitados ->> " + visited);
        for (String neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfsVisit(neighbor, visited, finished);
            }
        }
        finished.addFirst(node);
        System.out.println("Finalizados ->> " + finished);
    }

    /**
     *  Retora a transposta do grafo.
     */
    public Map<String, List<String>> getTranspose() {
        Map<String, List<String>> transpose = new HashMap<>();
        for (String node : graph.keySet()) {
            transpose.put(node, new ArrayList<>());
        }
        for (String node : graph.keySet()) {
            for (String neighbor : graph.get(node)) {
                transpose.get(neighbor).add(node);
            }
        }
        return transpose;
    }


    /**
     * Realiza DFS especifíca da transposta e retornando o resultado com os componentes finais.
     */
    public List<List<String>> dfsTranspose(Map<String, List<String>> transpose, Set<String> sortedNodes) {
        List<List<String>> result = new ArrayList<>();
        Set<String> visited = new LinkedHashSet<>();
        for (String node : sortedNodes) {
            if (!visited.contains(node)) {
                List<String> component = new ArrayList<>();
                dfsTransposeVisit(node, transpose, visited, component);
                result.add(component);
                System.out.println("Componente da transposta ->> " + component);
            }
        }
        return result;
    }

    /**
     * DFS Para cada vertice da transposta.
     */
    private void dfsTransposeVisit(String node, Map<String, List<String>> transpose, Set<String> visited, List<String> component) {
        visited.add(node);
        System.out.println("Visitados da transposta ->> " + visited);
        component.add(node);
        for (String neighbor : transpose.get(node)) {
            if (!visited.contains(neighbor)) {
                dfsTransposeVisit(neighbor, transpose, visited, component);
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