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

    public List<String> dfs() {

        LinkedList<String> finished = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        for (String node : graph.keySet()) {
            if (!visited.contains(node)) {
                dfsVisit(node, visited, finished);
            }
        }

        return finished;
    }

    private void dfsVisit(String node, Set<String> visited, LinkedList<String> finished) {
        visited.add(node);
        for (String neighbor : graph.get(node)) {
            if (!visited.contains(neighbor)) {
                dfsVisit(neighbor, visited, finished);
            }
        }
        finished.addFirst(node);
    }

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

    public List<List<String>> dfsTranspose(Map<String, List<String>> transpose, List<String> sortedNodes) {
        List<List<String>> result = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        for (String node : sortedNodes) {
            if (!visited.contains(node)) {
                List<String> component = new ArrayList<>();
                dfsTransposeVisit(node, transpose, visited, component);
                result.add(component);
            }
        }
        return result;
    }

    private void dfsTransposeVisit(String node, Map<String, List<String>> transpose, Set<String> visited, List<String> component) {
        visited.add(node);
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
        graph.put("a", Arrays.asList("d", "c", "e"));
        graph.put("b", Collections.singletonList("e"));
        graph.put("c", Arrays.asList("f", "g"));
        graph.put("d", Arrays.asList("b", "c", "e"));
        graph.put("e", Collections.singletonList("g"));
        graph.put("f", Arrays.asList("d", "g"));
        graph.put("g", Collections.singletonList("b"));
        InstagramCluster instagram = new InstagramCluster(graph);

        instagram.printGraph();

        System.out.println(instagram.dfs());

        System.out.println(instagram.graph);
        System.out.println(instagram.getTranspose());

        System.out.println(instagram.dfsTranspose(instagram.getTranspose(), instagram.dfs()));

    }
}