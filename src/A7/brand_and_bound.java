package A7;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class brand_and_bound {

    private static int best_so_far;
    private static WeightedGraph<String> graph;
    private static Node root;
	public static void main(String[] args){

	    best_so_far = 34;
        String[] vertices = {"a","b","c","d","e"};
        int[][] edges = {{0,1,3},{0,2,1},{0,3,5},{0,4,8},
                        {1,0,3},{1,2,6},{1,3,7},{1,4,9},
                        {2,0,1},{2,1,6},{2,3,4},{2,4,2},
                        {3,0,5},{3,1,7},{3,2,4},{3,4,3},
                        {4,0,8},{4,1,9},{4,2,2},{4,3,3}};
            graph = new WeightedGraph<>(edges,vertices);
            brand_and_bound test = new brand_and_bound();
            System.out.println(test.brand_and_bound_iter(0));
	}
	

	public int brand_and_bound_iter(int start){

		PriorityQueue<Node> queue = new PriorityQueue();
        root = new Node(1,0);
		queue.add(root);
        root.lb = getLowerBound(getLines(root,root));
		while(!queue.isEmpty()){
		    Node temp = queue.remove();

		    if(temp.lb < best_so_far && temp.length <= graph.getVertices().size()){

                List list = getSelectedLocation(temp);

		        for(int i=0;i<graph.getVertices().size();i++){
                    if(list.contains(i))
                        continue;
		            Node temp2 = new Node(temp.length+1,i);
                    temp2.parent = temp;
                    if(check(temp2))
                        continue;
                    List<WeightedEdge> lines = getLines(temp2,root);
		            temp2.lb = getLowerBound(lines);
		            if(temp2.length == graph.getVertices().size() && temp2.lb < best_so_far)//更新best_so_far
                        best_so_far = temp2.lb;
		            else
                        queue.add(temp2);
                }
            }
            else{
		        queue.remove(temp);//枝剪
            }
        }
		return best_so_far;
	}

	private boolean check(Node temp){

        if(temp.current == 2){

            boolean flag = true;
            while(temp.parent != null){
               temp = temp.parent;
               if(temp.current == 1)
                   flag = false;
            }
            if(flag)
                return true;
        }
        return false;
    }

	private List getSelectedLocation(Node temp){
	    List list = new ArrayList();
	    do{
            list.add(temp.current);
            temp = temp.parent;

        } while(temp != null);
	    return list;
    }

    private List<WeightedEdge> getLines(Node temp,Node root){

        List<WeightedEdge> lines = new ArrayList<>();
	    if(temp.length == graph.getVertices().size()){
            WeightedEdge line1 = new WeightedEdge(root.current,temp.current,-1);
            WeightedEdge line2 = new WeightedEdge(temp.current,root.current,-1);
            lines.add(line1);
            lines.add(line2);
        }

        while(temp.parent != null){
            WeightedEdge line1 = new WeightedEdge(temp.parent.current,temp.current,-1);
            WeightedEdge line2 = new WeightedEdge(temp.current,temp.parent.current,-1);
            lines.add(line1);
            lines.add(line2);
            temp = temp.parent;
        }

	    return lines;
    }
    /**
      * 计算新的lb
      * lines必须包含的边
      */
    public int getLowerBound(List<WeightedEdge> lines){

        //加的次数
        int count[] = new int[graph.getVertices().size()];
        int lb = 0;
        List<PriorityQueue<WeightedEdge>> edges = graph.deepClone(graph.getWeightedEdges());

        //遍历必须包含的边  u--->v
        for(int i=0;i<lines.size();i++){
            WeightedEdge temp = lines.get(i);
            PriorityQueue<WeightedEdge> queue = edges.get(temp.u);
            Object[] list = queue.toArray();
             for(int j=0;j<list.length;j++){
                 WeightedEdge cur = (WeightedEdge)list[j];
                 if(cur.v == lines.get(i).v){
                     lb += cur.weight;
                     count[cur.u]++;
                     edges.get(temp.u).remove(cur);//已经加过的边要删除
                 }
             }
        }
        //再加上剩下的权
        for(int i=0;i<edges.size();i++){
            PriorityQueue temp = edges.get(i);
            while(count[i] < 2){
                WeightedEdge a = (WeightedEdge) temp.remove();
                lb += a.weight;
                count[i]++;
            }
        }

        lb = lb%2==0?(lb/2):((lb+1)/2);
        //System.out.println("lb = "+lb);
        return lb;
    }

}
