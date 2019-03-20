package A3;



import A7.Graph;

import java.util.Stack;

public class tarjan {

    private boolean visited[] = new boolean[6];
    private static int numberOfVertices;
    private int dfn[] = new int[numberOfVertices];
    private int low[] = new int[numberOfVertices];
    private Stack stack = new Stack();
    private int index = 1;

    public static void main(String[] args){
        numberOfVertices = 6;
        int[][] edges = {
                {0,1}, {0,3},
                {1,2}, {1,4},
                {2,5},
                {3,4},
                {4,5},{4,0}
        };

        /*{0,1}, {0,2},
                {1,3},
                {2,3},{2,4},
                {3,5},{3,0},
                {4,5}*/
        Graph graph = new UnweightGraph<>(edges,numberOfVertices);
        tarjan test = new tarjan();
        test.tarjan(graph);
    }

    //tarjan算法
    public void tarjan(Graph graph){

        //for(int i=0;i<graph.getVertices().size();i++){
            dfs(graph,0);
        //}

    }

    public void dfs(Graph graph, int cur){

        //System.out.println("cur = "+cur);
        stack.push(graph.getVertices().get(cur));
        visited[cur] = true;//已访问
        dfn[cur] = low[cur] = index++;

        for(int i=0;i<graph.getNeighbors(cur).size();i++) {

            int next = (int)graph.getNeighbors(cur).get(i);
            //未访问过
            if (!visited[next]) {
                //继续向下dfs
                dfs(graph, (int)graph.getNeighbors(cur).get(i));
                //维护low的值
                low[cur] = low[cur] > low[next] ? low[next] : low[cur];

            }
            else if (stack.contains(next))
                low[cur] = low[cur] > dfn[next] ? dfn[next] : low[cur];

        }
        // 如果节点u是强连通分量的根
        if (dfn[cur] == low[cur]){
            int temp;
            do {
                temp = (int)stack.pop();
                System.out.print(temp+" ");
            }while(cur != temp);
            System.out.println();
        }

    }

}
