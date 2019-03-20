package A8;

import A7.WeightedEdge;
import A7.WeightedGraph;

import java.util.List;
import java.util.PriorityQueue;

public class BestLocation {

    private static int U = 10000;//Unreachable
    /*public static void main(String[] args){

        //权值 = 距离*频率
        String[] vertices = {"a","b","c","d"};
        int[][] graph = {{0,U,3,U},
                         {2,0,U,U},
                         {U,7,0,1},
                         {6,U,U,0}};

        BestLocation test = new BestLocation();
        System.out.println(vertices[test.getBestLocation(graph)]);
    }*/


    private int[][] turnGraph(WeightedGraph<String> A){
        int[][] graph = new int[A.getVertices().size()][A.getVertices().size()];
        for(int i=0;i<graph.length;i++){
            for(int j=0;j<graph[i].length;j++){
                graph[i][j] = U;
                if(i == j)
                    graph[i][j] = 0;
            }
        }
        List<PriorityQueue<WeightedEdge>> temp =  A.getWeightedEdges();
        for(int i=0;i<temp.size();i++){

            for(int j=0;j<temp.get(i).size();j++){
                while(temp.get(i).size() != 0){
                    WeightedEdge edge = temp.get(i).remove();
                    graph[edge.u][edge.v] = edge.weight;
                }

            }
        }


        for(int k=0;k<graph.length;k++) {
            for (int i = 0; i < graph.length; i++) {
                System.out.print(graph[k][i]+"  ");
            }
            System.out.println();
        }

        return graph;
    }
    
    //int[][] graph
    public int getBestLocation(WeightedGraph<String> A){
        int[][] graph = turnGraph(A);
        graph = Floyd(graph);
        int index = 0;
        int min = U;
        for(int i=0;i<graph.length;i++){
            int temp = 0;
            for(int j=0;j<graph.length;j++){
                temp += graph[i][j];
            }
            if(min > temp){
                min = temp;
                index = i;
            }
        }
        return index;
    }

    private int[][] Floyd(int[][] graph){


        int[][] R = graph;
        int[][] index = new int[graph.length][graph.length];

        for(int k=0;k<graph.length;k++){
            for(int i=0;i<graph.length;i++){
                for(int j=0;j<graph.length;j++){
                    index[i][j] = R[i][j]<R[i][k]+R[k][j]?R[i][j]:R[i][k]+R[k][j];
                }
            }
            R = index;
        }

        for(int k=0;k<index.length;k++) {
            for (int i = 0; i < index.length; i++) {
                System.out.print(index[k][i]+"  ");
            }
            System.out.println();
        }
        return index;
    }

}
