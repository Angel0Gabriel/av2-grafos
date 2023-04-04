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

    public static void main(String[] args) {
        InstagramCluster instagram = new InstagramCluster();
        instagram.addConnection(1, 2);
        instagram.addConnection(1, 3);
        instagram.addConnection(2, 3);
        instagram.addConnection(4, 5);
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