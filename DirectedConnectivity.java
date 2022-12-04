import java.util.*;

class Graph2{
    int vertices;
    int edges;
    ArrayList<ArrayList<Integer>> adjList;
    ArrayList<ArrayList<Integer>> transposeList;

    public Graph2(int Vertices,int edges){
        this.edges=edges;
        this.vertices=Vertices;
        adjList=new ArrayList<>();
        transposeList=new ArrayList<>();

        for(int i=0;i<vertices;i++){
            adjList.add(new ArrayList<>());
        }
        for(int i=0;i<vertices;i++){
            transposeList.add(new ArrayList<>());
        }
    }

    void addEdgeAdjList(int u,int v){
        adjList.get(u).add(v);
    }

    void addEdgeTrList(int u,int v){
        transposeList.get(u).add(v);
    }

    void printList(){
        for(int i=0;i<vertices;i++){
            System.out.print(i+" -> ");

            Iterator itr=transposeList.get(i).iterator();

            while(itr.hasNext()){
                int node=(Integer)itr.next();
                System.out.print(node+" ");
            }
            System.out.println();
        }
    }

    void getTranspose(){
        for(int i=0;i<vertices;i++){

            Iterator itr=adjList.get(i).iterator();
            while (itr.hasNext()){
                int node=(Integer)itr.next();
                addEdgeTrList(node,i);
            }
        }
    }


}


public class DirectedConnectivity {
    static  Scanner sc=new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("Enter number of vertices: ");
        int vertices = sc.nextInt();

        System.out.println("Enter number of edges: ");
        int edges = sc.nextInt();

        Graph2 g = new Graph2(vertices,edges);

        for (int i = 0; i < edges; i++) {
            System.out.println("Enter u,v");
            int u = sc.nextInt();
            int v = sc.nextInt();
            g.addEdgeAdjList(u, v);
        }
        g.getTranspose();
       g.printList();

    }
}
