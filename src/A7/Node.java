package A7;


public class Node implements Comparable<Node>{

   Node parent;
   int lb=0;
   int length = 0;
   int current = 0;

   public Node(){}

   public Node(int length,int current){
       this.length = length;
       this.current = current;
   }

   @Override
   public int compareTo(Node o) {

       if(lb > o.lb)
           return 1;
       else if(lb == o.lb)
           return 0;
       else
           return -1;
   }
}
