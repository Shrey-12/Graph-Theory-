import java.util.*;

public class Prim {
    public static void main(String[] args) {
        Graph g = new Graph(5);
        g.adjacencyList[0].add(new edge(1, 2));
        g.adjacencyList[1].add(new edge(0, 2));

        g.adjacencyList[1].add(new edge(2, 3));
        g.adjacencyList[2].add(new edge(1, 3));

        g.adjacencyList[0].add(new edge(3, 6));
        g.adjacencyList[3].add(new edge(0, 6));

        g.adjacencyList[1].add(new edge(3, 8));
        g.adjacencyList[3].add(new edge(1, 8));

        g.adjacencyList[1].add(new edge(4, 5));
        g.adjacencyList[4].add(new edge(1, 5));

        g.adjacencyList[2].add(new edge(4, 7));
        g.adjacencyList[4].add(new edge(2, 7));

        g.toPrimMSTMyHeap();

        g.printMST();
    }

}

class edge implements Comparable<edge> {
    private int vertice;
    private int weight;

    public edge(int vertice, int weight) {
        this.vertice = vertice;
        this.weight = weight;
    }

    public int getVertice() {
        return vertice;
    }

    public int getWeight() {
        return weight;
    }

    public int compareTo(edge B) {
        return Integer.compare(this.getWeight(), B.getWeight());
    }

}

class compareEdge implements Comparator<edge> {
    @Override
    public int compare(edge A, edge B) {
        return Integer.compare(A.getWeight(), B.getWeight());
    }
}

class Graph {
    private int vertices;
    LinkedList<edge>[] adjacencyList;
    private Integer[] PrimsMST;

    @SuppressWarnings("unchecked")
    public Graph(int size) {
        vertices = size;
        adjacencyList = new LinkedList[size];
        for (int j = 0; j < size; j++) {
            adjacencyList[j] = new LinkedList<edge>();
        }
    }

    void toPrimMSTBruteForce() {

        PrimsMST = new Integer[vertices];
        int minWeight[] = new int[vertices];
        boolean done[] = new boolean[vertices];

        for (int index = 0; index < vertices; index++) {
            minWeight[index] = Integer.MAX_VALUE;
            done[index] = false;
        }

        for (int i = 0; i < vertices; i++) {

            for (int j = 0; j < vertices - adjacencyList[i].size() + 3; j++) {
                adjacencyList[i].add(new edge(0, 0));
            }
        }

        minWeight[0] = 0;

        PrimsMST[0] = -1;

        for (int index = 0; index < vertices - 1; index++) {

            int minimumWeight = Integer.MAX_VALUE;
            int minimumWeightIndex = -1;
            for (int v = 0; v < vertices; v++) {
                if (done[v] == false && minWeight[v] < minimumWeight) {
                    minimumWeight = minWeight[v];
                    minimumWeightIndex = v;
                }
            }

            done[minimumWeightIndex] = true;

            for (edge curr : adjacencyList[minimumWeightIndex]) {
                if (done[curr.getVertice()] == false
                        && curr
                                .getWeight() < minWeight[curr.getVertice()]) {
                    PrimsMST[curr.getVertice()] = minimumWeightIndex;
                    minWeight[curr.getVertice()] = curr.getWeight();
                }

            }
        }
    }

    void toPrimMSTHeap() {

        PrimsMST = new Integer[vertices];
        int minWeight[] = new int[vertices];
        boolean done[] = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            minWeight[i] = Integer.MAX_VALUE;
            done[i] = false;
        }

        PriorityQueue<edge> heap = new PriorityQueue<edge>(vertices, new compareEdge());

        minWeight[0] = 0;
        PrimsMST[0] = -1;
        heap.add(new edge(minWeight[0], 0));
        while (!heap.isEmpty()) {
            int minimumWeightIndex = heap.poll().getVertice();
            done[minimumWeightIndex] = true;

            for (edge curr : adjacencyList[minimumWeightIndex]) {
                if (done[curr.getVertice()] == false && curr.getWeight() < minWeight[curr.getVertice()]) {
                    PrimsMST[curr.getVertice()] = minimumWeightIndex;
                    minWeight[curr.getVertice()] = curr.getWeight();
                    heap.add(new edge(curr.getVertice(), minWeight[curr.getVertice()]));
                }
            }
        }

    }

    void toPrimMSTMyHeap() {

        PrimsMST = new Integer[vertices];
        int minWeight[] = new int[vertices];
        boolean done[] = new boolean[vertices];
        for (int i = 0; i < vertices; i++) {
            minWeight[i] = Integer.MAX_VALUE;
            done[i] = false;
        }

        XminHeap<edge> heap = new XminHeap<edge>(vertices);

        minWeight[0] = 0;
        PrimsMST[0] = -1;
        heap.push(new edge(minWeight[0], 0));
        while (!heap.isEmpty()) {
            int minimumWeightIndex = heap.extractMin().getVertice();
            done[minimumWeightIndex] = true;

            for (edge curr : adjacencyList[minimumWeightIndex]) {
                if (done[curr.getVertice()] == false && curr.getWeight() < minWeight[curr.getVertice()]) {
                    PrimsMST[curr.getVertice()] = minimumWeightIndex;
                    minWeight[curr.getVertice()] = curr.getWeight();
                    heap.push(new edge(curr.getVertice(), minWeight[curr.getVertice()]));
                }
            }
        }

    }

    public void printMST() {
        for (int index = 1; index < vertices; index++) {
            System.out.println(PrimsMST[index] + " : " + index);
        }
    }
}

class minHeap {
    private ArrayList<edge> heap;

    public minHeap() {
        heap = new ArrayList<edge>();
    }

    public minHeap(int size) {
        heap = new ArrayList<edge>(size);
    }

    public minHeap(edge first) {
        heap = new ArrayList<edge>(10);
        heap.set(0, first);
    }

    private void heapifyDown(int root) {
        int smallest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;
        if (left < heap.size() && heap.get(left).getWeight() < heap.get(smallest).getWeight())
            smallest = left;

        if (right < heap.size() && heap.get(right).getWeight() < heap.get(smallest).getWeight())
            smallest = right;

        if (smallest != root) {
            edge swap = heap.get(root);
            heap.set(root, heap.get(smallest));
            heap.set(smallest, swap);

            heapifyDown(smallest);
        }
    }

    public edge extractMin() {
        if (isEmpty()) {
            System.out.println("Heap is Empty");
            return null;
        }
        edge minimum = heap.get(0);
        heap.set(0, heap.get(heap.size() - 1));
        heap.remove(heap.size() - 1);
        heapifyDown(0);
        return minimum;

    }

    public edge getMin() {
        if (isEmpty()) {
            System.out.println("Heap is Empty");
            return null;
        }
        return heap.get(0);
    }

    public Boolean isEmpty() {
        if (heap.size() == 0)
            return true;
        return false;
    }

    public void push(edge fresh) {
        heap.add(fresh);
        heapifyDown(0);
    }

    public int size() {
        return heap.size();
    }
}

class XminHeap<T extends Comparable<T>> {
    private ArrayList<T> heap;

    public XminHeap() {
        heap = new ArrayList<T>();
    }

    public XminHeap(int size) {
        heap = new ArrayList<T>(size);
    }

    public XminHeap(T first) {
        heap = new ArrayList<T>(10);
    }

    private void heapifyDown(int root) {
        int smallest = root;
        int left = 2 * root + 1;
        int right = 2 * root + 2;

        if (left < heap.size() && heap.get(left).compareTo(heap.get(smallest)) < 1)
            smallest = left;

        if (right < heap.size() && heap.get(right).compareTo(heap.get(smallest)) < 1)
            smallest = right;

        if (smallest != root) {
            T swap = heap.get(root);
            heap.set(root, heap.get(smallest));
            heap.set(smallest, swap);

            heapifyDown(smallest);
        }
    }

    public T extractMin() {
        if (isEmpty()) {
            System.out.println("Heap is Empty");
            return null;
        }
        T minimum = heap.get(0);
        heap.remove(0);
        heapifyDown(0);
        return minimum;

    }

    public T getMin() {
        if (isEmpty()) {
            System.out.println("Heap is Empty");
            return null;
        }
        return heap.get(0);
    }

    public Boolean isEmpty() {
        if (heap.size() == 0)
            return true;
        return false;
    }

    public void push(T fresh) {
        heap.add(fresh);
        heapifyDown(0);
    }

    public int size() {
        return heap.size();
    }
}