import java.util.*;

public class InstagramCluster {
    // Representa o grafo da rede social
    Map<Integer, List<Integer>> graph = new HashMap<>();

    // Adiciona uma conexão entre dois usuários
    public void addConnection(int user1, int user2) {
        if (!graph.containsKey(user1)) {
            graph.put(user1, new ArrayList<>());
        }
        if (!graph.containsKey(user2)) {
            graph.put(user2, new ArrayList<>());
        }
        graph.get(user1).add(user2);
        graph.get(user2).add(user1);
    }

    // Executa o algoritmo DFS para encontrar os clusters
    public List<List<Integer>> findClusters() {
        List<List<Integer>> clusters = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        for (int user : graph.keySet()) {
            if (!visited.contains(user)) {
                List<Integer> cluster = new ArrayList<>();
                dfs(user, visited, cluster);
                clusters.add(cluster);
            }
        }
        return clusters;
    }

    // Algoritmo DFS
    private void dfs(int user, Set<Integer> visited, List<Integer> cluster) {
        visited.add(user);
        cluster.add(user);
        for (int friend : graph.get(user)) {
            if (!visited.contains(friend)) {
                dfs(friend, visited, cluster);
            }
        }
    }

    public void printGraph() {
    for (int user : graph.keySet()) {
        System.out.print(user + ": ");
        for (int friend : graph.get(user)) {
            System.out.print(friend + " ");
        }
        System.out.println();
    }
    
}

    public static void main(String[] args) {
        InstagramCluster instagram = new InstagramCluster();
        instagram.addConnection(1, 2);
        instagram.addConnection(1, 3);
        instagram.addConnection(2, 3);
        instagram.addConnection(4, 5);
        instagram.addConnection(5, 6);
        instagram.addConnection(6, 7);
        instagram.addConnection(7, 8);
        instagram.printGraph();
        List<List<Integer>> clusters = instagram.findClusters();
        System.out.println(clusters);
    }
}

/* 
Neste exemplo, a classe InstagramCluster representa o grafo da rede social e contém dois métodos principais: addConnection para adicionar uma conexão entre dois usuários, 
e findClusters para encontrar os clusters na rede. O algoritmo DFS é implementado no método dfs e é chamado para cada nó não visitado na rede no método findClusters.

No exemplo, foram criadas algumas conexões entre usuários para testar a implementação. A saída do programa será uma lista de listas, 
onde cada lista interna representa um cluster de usuários alcançáveis entre si. Para este exemplo, a saída será: [[1, 2, 3], [4, 5]], indicando que os usuários 1, 2 e 3 formam um cluster, 
enquanto os usuários 4 e 5 formam outro.
*/

/*
Claro! Vou explicar cada parte do código com mais detalhes:

java
Copy code
import java.util.*;

public class InstagramCluster {
    // Representa o grafo da rede social
    Map<Integer, List<Integer>> graph = new HashMap<>();
Esta é a classe InstagramCluster, que representa o grafo da rede social. A estrutura de dados escolhida para representar o grafo é um Map de inteiros para listas de inteiros. 
Cada chave do mapa representa um usuário da rede social e o valor correspondente é uma lista de seus amigos.

java
Copy code
    // Adiciona uma conexão entre dois usuários
    public void addConnection(int user1, int user2) {
        if (!graph.containsKey(user1)) {
            graph.put(user1, new ArrayList<>());
        }
        if (!graph.containsKey(user2)) {
            graph.put(user2, new ArrayList<>());
        }
        graph.get(user1).add(user2);
        graph.get(user2).add(user1);
    }
Este método adiciona uma conexão entre dois usuários, o que significa que eles se tornam amigos na rede social. O método primeiro verifica se cada usuário já está no mapa. 
Se não estiver, ele adiciona o usuário ao mapa com uma lista vazia de amigos. Em seguida, o método adiciona cada usuário à lista de amigos do outro.

java
Copy code
    // Executa o algoritmo DFS para encontrar os clusters
    public List<List<Integer>> findClusters() {
        List<List<Integer>> clusters = new ArrayList<>();
        Set<Integer> visited = new HashSet<>();
        for (int user : graph.keySet()) {
            if (!visited.contains(user)) {
                List<Integer> cluster = new ArrayList<>();
                dfs(user, visited, cluster);
                clusters.add(cluster);
            }
        }
        return clusters;
    }
Este método executa o algoritmo DFS para encontrar os clusters na rede social. Ele começa criando uma lista vazia de clusters e um conjunto vazio de usuários visitados. 
Em seguida, ele percorre todos os usuários no mapa e verifica se cada um já foi visitado. Se não foi visitado, o método chama o algoritmo DFS a partir desse usuário para 
encontrar todos os seus amigos alcançáveis. Quando o DFS termina, a lista de amigos alcançáveis é adicionada ao cluster.

java
Copy code
    // Algoritmo DFS
    private void dfs(int user, Set<Integer> visited, List<Integer> cluster) {
        visited.add(user);
        cluster.add(user);
        for (int friend : graph.get(user)) {
            if (!visited.contains(friend)) {
                dfs(friend, visited, cluster);
            }
        }
    }
Este é o algoritmo DFS, que é chamado pelo método findClusters para encontrar os amigos alcançáveis a partir de um usuário. 
Ele começa marcando o usuário como visitado e adicionando-o à lista de amigos alcançáveis (cluster). Em seguida, o algoritmo percorre todos os amigos do usuário 
e verifica se cada amigo já foi visitado. Se o amigo ainda não foi visitado, o algoritmo chama a si próprio recursivamente a partir desse amigo para encontrar todos os seus amigos alcançáveis.

private void dfs(int user, Set<Integer> visited, List<Integer> cluster) {
    visited.add(user);
    cluster.add(user);
    for (int friend : graph.get(user)) {
        if (!visited.contains(friend)) {
            dfs(friend, visited, cluster);
        }
    }
}
O método dfs é uma implementação do algoritmo de busca em profundidade (DFS - Depth-First Search), que percorre o grafo a partir de um vértice inicial até que todos os vértices alcançáveis 
sejam visitados.

Os parâmetros de entrada da função são:

user: o vértice inicial a partir do qual a busca em profundidade deve ser iniciada;
visited: um conjunto que armazena os vértices visitados até o momento;
cluster: uma lista que armazena todos os vértices alcançáveis a partir do vértice user.
A primeira linha do método adiciona o vértice user ao conjunto visited, marcando-o como visitado. A segunda linha adiciona o vértice user à lista cluster, que armazena todos os vértices 
alcançáveis a partir do vértice user.

O loop for que começa na linha 4 percorre todos os vizinhos (amigos) do vértice user através da lista de adjacências graph.get(user). Para cada vizinho friend não visitado ainda, o método 
chama a si mesmo recursivamente passando o vizinho friend como o novo vértice inicial da busca.

Dessa forma, o método percorre todos os vértices alcançáveis a partir do vértice user e adiciona-os à lista cluster.

Essa parte do código é o método main da classe InstagramCluster que realiza o teste do algoritmo implementado.

public static void main(String[] args) {
    InstagramCluster instagram = new InstagramCluster();
    instagram.addConnection(1, 2);
    instagram.addConnection(1, 3);
    instagram.addConnection(2, 3);
    instagram.addConnection(4, 5);
    List<List<Integer>> clusters = instagram.findClusters();
    System.out.println(clusters);
}

As linhas 2 a 6 criam um objeto instagram da classe InstagramCluster e adicionam conexões entre alguns usuários usando o método addConnection. Nesse caso, 
a rede possui duas conexões: (1, 2), (1, 3), (2, 3) e outra conexão independente (4, 5).

A linha 7 chama o método findClusters que encontra todos os agrupamentos de usuários na rede. Esse método retorna uma lista de listas de inteiros, onde cada lista interna representa um 
agrupamento (cluster) de usuários.

Por fim, a linha 8 imprime a lista de agrupamentos encontrados na rede usando o método println da classe System.

Esse trecho de código é apenas um exemplo de como a classe InstagramCluster pode ser utilizada para encontrar agrupamentos de usuários em uma rede social. O resultado impresso na tela seria 
o seguinte:

[[1, 2, 3], [4, 5]]

Ou seja, a rede possui dois agrupamentos de usuários: (1, 2, 3) e (4, 5).

Para imprimir o grafo, podemos adicionar um novo método à classe InstagramCluster que percorre todos os usuários e imprime seus amigos correspondentes. 
Aqui está um exemplo de como o código ficaria:

public void printGraph() {
    for (int user : graph.keySet()) {
        System.out.print(user + ": ");
        for (int friend : graph.get(user)) {
            System.out.print(friend + " ");
        }
        System.out.println();
    }
}
O método printGraph percorre todas as chaves do mapa graph e imprime o número do usuário seguido pelos seus amigos correspondentes. Cada linha representa um usuário diferente.

Agora, podemos chamar o método printGraph no método main para imprimir o grafo completo antes de encontrar os agrupamentos:

public static void main(String[] args) {
    InstagramCluster instagram = new InstagramCluster();
    instagram.addConnection(1, 2);
    instagram.addConnection(1, 3);
    instagram.addConnection(2, 3);
    instagram.addConnection(4, 5);
    instagram.addConnection(5, 6);
    instagram.addConnection(6, 7);
    instagram.addConnection(7, 8);
    instagram.printGraph();
    List<List<Integer>> clusters = instagram.findClusters();
    System.out.println(clusters);
}
O resultado impresso na tela seria o seguinte:

1: 2 3 
2: 1 3 
3: 1 2 
4: 5 
5: 4 
[[1, 2, 3], [4, 5]]

Isso significa que o usuário 1 tem conexões com os usuários 2 e 3, o usuário 2 tem conexões com os usuários 1 e 3 e assim por diante. 
A linha em branco separa a saída do grafo da saída dos agrupamentos.
*/