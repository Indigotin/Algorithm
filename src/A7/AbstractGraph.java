package A7;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AbstractGraph<V> implements Graph<V> {
    protected List<V> vertices;
    protected List<List<Integer>> neighbors;

    //二维数组+顶点
    protected AbstractGraph(int[][] edges,V[] vertices){
        this.vertices = new ArrayList<V>();
        for(int i=0;i<vertices.length;i++){
            this.vertices.add(vertices[i]);
        }
        creatAdjacencyList(edges,vertices.length);
    }
    //Edges类+顶点
    protected AbstractGraph(List<Edges> edges,List<V> vertices){
        this.vertices = vertices;
        creatAdjacencyList(edges,vertices.size());
    }
    //Edges类+顶点个数
    protected AbstractGraph(List<Edges> edges,int numberOfVertices){
        this.vertices = new ArrayList<V>();
        for(int i=0;i<numberOfVertices;i++){
            vertices.add((V)new Integer((i)));
        }
        creatAdjacencyList(edges,numberOfVertices);
    }
    //二维数组+顶点个数
    protected AbstractGraph(int[][] edges,int numberOfVertices){
        this.vertices = new ArrayList<V>();
        for(int i=0;i<numberOfVertices;i++){
            vertices.add((V)new Integer((i)));
        }
        creatAdjacencyList(edges,numberOfVertices);
    }
    //由二维数组创建边对象
    private void creatAdjacencyList(int[][] edges,int numberOfVertices){
        this.neighbors = new ArrayList<>();
        for(int i=0;i<numberOfVertices;i++){
            this.neighbors.add(new ArrayList<>());
        }
        for(int i=0;i<edges.length;i++){
            int u = edges[i][0];//二维数组
            int v = edges[i][1];
            neighbors.get(u).add(v);
        }
    }
    //由Edges类创建边对象
    private void creatAdjacencyList(List<Edges> edges,int numberOfVertices){
        this.neighbors = new ArrayList<>();
        for(int i=0;i<numberOfVertices;i++){
            this.neighbors.add(new ArrayList<>());
        }
        for(int i=0;i<edges.size();i++){
            int u = edges.get(i).u;
            int v = edges.get(i).v;
            neighbors.get(u).add(v);
        }
    }
    @Override
    public int getSize() {
        return vertices.size();
    }

    @Override
    public List<V> getVertices() {
        return vertices;
    }

    @Override
    public V getVertex(int index) {
        return vertices.get(index);
    }

    @Override
    public int getIndex(V v) {
        return vertices.indexOf(v);
    }

    @Override
    public List<Integer> getNeighbors(int index) {
        return neighbors.get(index);
    }

    @Override
    public int getDegree(int v) {
        return neighbors.get(v).size();
    }

    @Override
    public int[][] getAdjacencyMatrix(){

        int[][] matrix = new int[getSize()][];
        for(int i=0;i<neighbors.size();i++){
            for(int j=0;j<neighbors.get(i).size();j++){
                matrix[i][neighbors.get(i).get(j)] = 1;
            }
        }
        return matrix;
    }

    @Override
    public void printAdjacencyMatrix() {

        int[][] matrix = getAdjacencyMatrix();
        for(int i=0;i<matrix.length;i++){
            for(int j=0;j<matrix[i].length;j++){
                System.out.print(matrix[i][j]+" ");
            }
            System.out.println();
        }
    }

    @Override
    public void printEdges() {

        for(int i=0;i<neighbors.size();i++){
            System.out.print("Vertex "+i+": ");
            for(int j=0;j<neighbors.get(i).size();j++){
                System.out.print(i+" -> "+neighbors.get(i).get(j)+" ");
            }
        }
    }

    @Override //Depth-First Search
    public Tree dfs(int v) {

        int[] parent = new int[getSize()];
        for(int i=0;i<parent.length;i++){
            parent[i] = -1;
        }

        boolean[] isVisited = new boolean[getSize()];
        for(int i = 0;i<isVisited.length;i++){
            isVisited[i] = false;
        }
        List<Integer> searchOrder = new ArrayList<>();

        dfs(v,isVisited,parent,searchOrder);
        //Test
        for(int i = 0;i<searchOrder.size();i++){
            System.out.print(searchOrder.get(i)+"  parent:"+parent[i]+"  ");
        }
        return new Tree(v,parent,searchOrder);
    }

    private void dfs(int v,boolean[] isVisited,int[] parent,List<Integer> searchOrder){
        boolean flag = true;
        for(int i = 0;i<isVisited.length;i++){
            if(isVisited[i] == false){
                flag = false;
            }
        }
        //如果全部已访问 则递归结束
        if(flag){
            return;
        }
        /*if(isVisited[v] == true){
            return;
        }*/
        searchOrder.add(v);
        isVisited[v] = true;

        for(int i = 0;i<neighbors.get(v).size();i++){

            //如果下一个点未被访问
            if(isVisited[neighbors.get(v).get(i)] != true){

                parent[neighbors.get(v).get(i)] = v;

                dfs(neighbors.get(v).get(i),isVisited,parent,searchOrder);
            }
        }
    }


    @Override //Breadth First Search
    public Tree bfs(int v) {
        List<Integer> searchOrder = new ArrayList<>();
        int[] parent = new int[getSize()];
        for(int i=0;i<parent.length;i++){
            parent[i] = -1;
        }
        boolean[] isVisited = new boolean[getSize()];
        for(int i =0;i<isVisited.length;i++){
            isVisited[i] = false;
        }
        LinkedList<Integer> queue = new LinkedList<>();

        queue.add(v);
        searchOrder.add(v);
        isVisited[v] = true;

        //queue非空时继续否则算法结束
        while(!queue.isEmpty()){

            for(int i=0;i<neighbors.get(queue.getFirst()).size();i++){

                if(isVisited[neighbors.get(queue.getFirst()).get(i)] != true){
                    queue.add(neighbors.get(queue.getFirst()).get(i));
                    parent[neighbors.get(queue.getFirst()).get(i)] = queue.getFirst();
                    isVisited[neighbors.get(queue.getFirst()).get(i)] = true;
                    searchOrder.add(neighbors.get(queue.getFirst()).get(i));
                }
            }
            queue.removeFirst();
        }

        //Test
        System.out.println(searchOrder.size());
        for(int i = 0;i<searchOrder.size();i++){

            System.out.print(searchOrder.get(i)+"  parent:"+parent[i]+"  ");
        }

        return new Tree(v,parent,searchOrder);
    }

    //test
    public static void main(String[] args){
        int[][] edges = {{0,1},{0,2},{0,3},
                {1,0},{1,2},{1,4},
                {2,0},{2,1},{2,3},
                {3,0},{3,2},{3,4},
                {4,1},{4,3}};
        int numberOfVertex = 5;

        AbstractGraph test = new AbstractGraph(edges,numberOfVertex);
        test.dfs(0);
        System.out.println();
        test.bfs(0);
    }

    @Override
    public List<Integer> getHamiltonianPath(V vertex) {
        return null;
    }

    @Override
    public List<Integer> getHamiltonianPath(int inexe) {
        return null;
    }

    //内部类
    public static class Edges{
        public int u;
        public int v;
        public Edges(int u,int v){
            this.u = u;
            this.v = v;
        }
    }

    //内部类
    public class Tree{
        private int root;
        private int[] parent;
        private List<Integer> searchOrders;

        public Tree(int root,int[] parent,List<Integer> searchOrders){
            this.root = root;
            this.parent = parent;
            this.searchOrders = searchOrders;
        }
        public Tree(int root,int[] parent){
            this.root = root;
            this.parent = parent;
        }

        public int getRoot(){
            return root;
        }

        public int getParent(int v){
            return parent[v];
        }

        public List<Integer> getSearchOrders() {
            return searchOrders;
        }
        public int getNumberOfVerticesFound(){
            return searchOrders.size();
        }
        //vertex V -> root
        public List<V> getPath(int index){
            ArrayList<V> path = new ArrayList<>();

            do{
                path.add(vertices.get(index));
                index = parent[index];

            }while(index != -1);

            return path;
        }

        //root -> vertex V
        public void printPath(int index){

            List<V> path = getPath(index);
            for(int i=0;i<path.size();i++){
                System.out.print(path.get(i)+" ");
            }
        }
        public void printTree(){
            System.out.println("root:"+root);
            System.out.print("Edges: ");
            for(int i=0;i<parent.length;i++){
                System.out.println(vertices.get(parent[i])+" -> "+vertices.get(i));
            }
        }

    }
}
