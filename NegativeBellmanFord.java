import java.util.*;

public class NegativeBellmanFord {

    static ArrayList<Integer> NegCycleBellmanFord(Graph graph, int source) {
        int V = graph.vertices;
        int[] distance = new int[V];
        int[] parent = new int[V];

        for (int i = 0; i < V; i++) {
            distance[i] = Integer.MAX_VALUE;
            parent[i] = -1;
        }
        distance[source] = 0;

        for (int i = 1; i <= V - 1; i++) {
            for (int index = 0; index < graph.adjacencyList.length; index++) {
                for (Edge edge : graph.adjacencyList[index]) {
                    int u = index;
                    int v = edge.end;
                    int weight = edge.weight;
                    relax(distance, parent, weight, u, v);

                }
            }
        }

        int check = -1;
        for (int index = 0; index < graph.adjacencyList.length; index++) {
            for (Edge edge : graph.adjacencyList[index]) {
                int u = index;
                int v = edge.end;
                int weight = edge.weight;

                if (distance[u] != Integer.MAX_VALUE &&
                        distance[u] + weight < distance[v]) {

                    check = v;
                    break;
                }
            }
        }

        if (check != -1) {
            for (int i = 0; i < V; i++)
                check = parent[check];

            ArrayList<Integer> cycle = new ArrayList<>();
            for (int v = check;; v = parent[v]) {
                cycle.add(v);

                if (v == check && cycle.size() > 1)
                    break;
            }

            Collections.reverse(cycle);
            for (int v : cycle) {
                System.out.print(v + " ");
            }

            System.out.println();
            cycle.remove(0);
            return cycle;
        } else
            System.out.println(-1);
        return new ArrayList<>();

    }

    public static void relax(int[] distance, int[] parent, int weight, int u, int v) {
        if (distance[u] != Integer.MAX_VALUE &&
                distance[u] + weight < distance[v]) {
            distance[v] = distance[u] + weight;
            parent[v] = u;
        }

    }

    public static void main(String[] args) {

        int V = 7;

        Graph graph = new Graph(V);

        graph.addEdge(1, 2, -3);
        graph.addEdge(2, 3, -2);
        graph.addEdge(3, 1, -1);
        graph.addEdge(3, 0, 4);
        graph.addEdge(3, 4, 2);
        graph.addEdge(4, 0, 1);
        graph.addEdge(5, 2, 1);
        graph.addEdge(6, 1, 3);

        ArrayList<Integer> cycle = NegCycleBellmanFord(graph, 1);
        boolean[] visited = new boolean[V];
        for (int i = 0; i < visited.length; i++)
            visited[i] = false;

        for (Integer i : cycle) {
            visited[i] = true;
        }
        Graph transpose = graph.getTranspose();
        for (Integer i : cycle) {
            LinkedList<Edge> edges = transpose.adjacencyList[i];
            for (Edge edge : edges) {
                if (visited[edge.end] == false) {
                    System.out.println(i + " " + edge.end);
                    visited[edge.end] = true;

                }
            }
        }
    }
}

class Edge {
    int end, weight;

    Edge(int end, int w) {
        this.end = end;
        weight = w;
    }
}

class Graph {
    int vertices;
    int edges;
    LinkedList<Edge>[] adjacencyList;

    @SuppressWarnings("unchecked")
    public Graph(int size) {
        vertices = size;
        adjacencyList = new LinkedList[size];
        for (int j = 0; j < size; j++) {
            adjacencyList[j] = new LinkedList<Edge>();
        }
    }

    public void addEdge(int u, int v, int weight) {
        for (int i = 0; i < adjacencyList.length; i++) {
            if (i == u) {
                adjacencyList[i].add(new Edge(v, weight));
            }
        }
        edges++;
    }

    Graph getTranspose() {
        Graph g = new Graph(vertices);
        for (int v = 0; v < vertices; v++) {
            for (Edge edge : adjacencyList[v]) {
                g.addEdge(edge.end, v, edge.weight);
            }
        }
        return g;
    }

}
