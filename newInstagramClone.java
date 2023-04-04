import java.util.*;

public class InstagramCluster {
    Map<String, List<String>> graph = new HashMap<>();

    public void addConnection(String user1, String user2) {
        if (!graph.containsKey(user1)) {
            graph.put(user1, new ArrayList<>());
        }
        if (!graph.containsKey(user2)) {
            graph.put(user2, new ArrayList<>());
        }
        graph.get(user1).add(user2);
    }

    public void addTConnection(String user1, String user2, Map<String, List<String>> graphToConnect) {
        if (!graphToConnect.containsKey(user1)) {
            graphToConnect.put(user1, new ArrayList<>());
        }
        if (!graphToConnect.containsKey(user2)) {
            graphToConnect.put(user2, new ArrayList<>());
        }
        graphToConnect.get(user1).add(user2);
    }

    public Map<String, List<String>> getTransposedGraph() {
        Map<String, List<String>> newGraph = new HashMap<>();

        for (String user : graph.keySet()) {
            for (String friend : graph.get(user)) {
                addTConnection(friend, user, newGraph);
            }
        }

        return newGraph;
    }

    public List<List<String>> findTClusters(Map<String, List<String>> graphToFind) {
        List<List<String>> clusters = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String user : graphToFind.keySet()) {
            if (!visited.contains(user)) {
                List<String> cluster = new ArrayList<>();
                dfsT(user, visited, cluster, graphToFind);
                clusters.add(cluster);
            }
        }
        return clusters;
    }

    public List<List<String>> findClusters() {
        List<List<String>> clusters = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String user : graph.keySet()) {
            if (!visited.contains(user)) {
                List<String> cluster = new ArrayList<>();
                dfs(user, visited, cluster);
                clusters.add(cluster);
            }
        }
        return clusters;
    }

    private void dfsT(String user, Set<String> visited, List<String> cluster, Map<String, List<String>> graphToFind) {
        visited.add(user);
        cluster.add(user);
        for (String friend : graphToFind.get(user)) {
            if (!visited.contains(friend)) {
                dfs(friend, visited, cluster);
            }
        }
    }

    private void dfs(String user, Set<String> visited, List<String> cluster) {
        visited.add(user);
        cluster.add(user);
        for (String friend : graph.get(user)) {
            if (!visited.contains(friend)) {
                dfs(friend, visited, cluster);
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

    public void printTGraph(Map<String,List<String>> graphToPrint) {
        for (String user : graphToPrint.keySet()) {
            System.out.print(user + ": ");
            for (String friend : graphToPrint.get(user)) {
                System.out.print(friend + " ");
            }
            System.out.println();
        }
    }


    public static void main(String[] args) {
        InstagramCluster instagram = new InstagramCluster();


        instagram.addConnection("a", "c");
        instagram.addConnection("a", "d");
        instagram.addConnection("a", "e");
        instagram.addConnection("b", "e");
        instagram.addConnection("c", "g");
        instagram.addConnection("c", "f");
        instagram.addConnection("d", "b");
        instagram.addConnection("d", "c");
        instagram.addConnection("d", "e");
        instagram.addConnection("e", "g");
        instagram.addConnection("f", "d");
        instagram.addConnection("f", "g");
        instagram.addConnection("g", "b");
        instagram.printGraph();



        List<List<String>> clusters = instagram.findClusters();
        System.out.println(clusters);



        List<List<String>> clustersT =  instagram.findTClusters(instagram.getTransposedGraph());
        System.out.println(clustersT);

    }
}
