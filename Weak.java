import java.util.ArrayList;

class Graph {
	int vertices;
	ArrayList<ArrayList<Integer> > adjacencyList;

	public Graph(int vertices)
	{
		this.vertices = vertices;
		adjacencyList = new ArrayList<>(vertices);

		for (int i = 0; i < this.vertices; i++)
			adjacencyList.add(new ArrayList<>());
	}

	public void addEdge(int u, int v)
	{
		if (noEdge(u, v))
			adjacencyList.get(u).add(v);
	}

	boolean noEdge(int u, int v)
	{
		for (int destination : adjacencyList.get(u))
			if (destination == v)
				return false;
		return true;
	}
}

class WCC {
	private final Graph directedGraph;

	public WCC(Graph directedGraph)
	{
		this.directedGraph = directedGraph;
	}

	private ArrayList<ArrayList<Integer> >
	connectedComponents(Graph undirectedGraph)
	{
		ArrayList<ArrayList<Integer> > connectedComponents
			= new ArrayList<>();
		boolean[] isVisited
			= new boolean[undirectedGraph.vertices];

		for (int i = 0; i < undirectedGraph.vertices; i++) {
			if (!isVisited[i]) {
				ArrayList<Integer> component
					= new ArrayList<>();
				findConnectedComponent(i, isVisited,
									component,
									undirectedGraph);
				connectedComponents.add(component);
			}
		}

		return connectedComponents;
	}

	private void
	findConnectedComponent(int src, boolean[] isVisited,
						ArrayList<Integer> component,
						Graph undirectedGraph)
	{
		isVisited[src] = true;
		component.add(src);

		for (int v :
			undirectedGraph.adjacencyList.get(src))
			if (!isVisited[v])
				findConnectedComponent(v, isVisited,
									component,
									undirectedGraph);
	}

	public ArrayList<ArrayList<Integer> >
	weaklyConnectedComponents()
	{
		Graph undirectedGraph
			= new Graph(directedGraph.vertices);
		for (int u = 0; u < directedGraph.vertices; u++) {
			for (int v :
				directedGraph.adjacencyList.get(u)) {
				undirectedGraph.addEdge(u, v);
				undirectedGraph.addEdge(v, u);
			}
		}

		return connectedComponents(undirectedGraph);
	}
}

public class Weak {
	public static void main(String[] args)
	{
		Graph directedGraph = new Graph(6);

		directedGraph.addEdge(0, 1);
		directedGraph.addEdge(0, 2);
		directedGraph.addEdge(3, 1);
		directedGraph.addEdge(3, 2);
		directedGraph.addEdge(4, 5);

		ArrayList<ArrayList<Integer> >
			weaklyConnectedComponents
			= new WCC(directedGraph)
				.weaklyConnectedComponents();

		int index = 1;
		for (ArrayList<Integer> component :
			weaklyConnectedComponents) {
			System.out.print("Component "
							+ index++ + ": ");
			for (Integer i : component)
				System.out.print(i + " ");
			System.out.println();
		}
	}
}
