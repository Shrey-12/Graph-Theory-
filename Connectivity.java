import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

//undirected graph in adjacency list
//number of connected components
//print vertices in all connected components
class Graph{
    static int connectedCount;
    int vertices;
    ArrayList<ArrayList<Integer>>adjList;

    public Graph(int Vertices){
        this.vertices=Vertices;
        adjList=new ArrayList<>();

        for(int i=0;i<vertices;i++){
            adjList.add(new ArrayList<>());
        }
    }

    void addEdge(int source,int destination){
        adjList.get(source).add(destination);
        adjList.get(destination).add(source);
    }

    void DFS(int vertex,int[] visited,int componentNum){
        //Mark the current vertex as visited
        visited[vertex]=componentNum;
        System.out.print(vertex+" ");
        for(int x:adjList.get(vertex)){
            if(visited[x]==0)
                DFS(x,visited,componentNum);
        }
    }

    int[] connectedComponents(int[] visited){
        //Mark all the vertices as not visited
        int componentNum=0;
        visited=new int[vertices];
        for(int i=0;i<vertices;i++){
            if(visited[i]==0){
                DFS(i,visited,++componentNum);
                connectedCount++;
                System.out.println();
            }
        }
        return visited;
    }

    static boolean checkIfConnected(int[] visited,int node1,int node2){
        if(visited[node1]==visited[node2])
            return true;
        else
            return false;
    }
}


public class Connectivity {
    static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Enter number of vertices: ");
        int vertices=sc.nextInt();
        Graph g=new Graph(vertices);

        System.out.println("Enter number of edges: ");
        int edges=sc.nextInt();
        for(int i=0;i<edges;i++){
            System.out.println("Enter u,v");
            int u=sc.nextInt();
            int v=sc.nextInt();
            g.addEdge(u,v);
            g.addEdge(v,u);}
        System.out.println("Connected components are: ");

        int[] visited=new int[vertices];
        visited=g.connectedComponents(visited);
        System.out.println("Number of connected components: "+Graph.connectedCount);

        System.out.println("Enter 2 nodes: ");
        int node1=sc.nextInt();
        int node2=sc.nextInt();
        System.out.println(Graph.checkIfConnected(visited,node1,node2));
        for(int i=0;i<visited.length;i++){
            System.out.print(visited[i]+" ");
        }




    }
}
