package A7;


public class WeightedEdge extends AbstractGraph.Edges implements Comparable<WeightedEdge> {
    public int weight;

    public WeightedEdge(int u, int v,int weight) {
        super(u, v);
        this.weight = weight;
    }

    @Override
    public int compareTo(WeightedEdge edge) {
        if(weight > edge.weight)
            return 1;
        else if(weight == edge.weight)
            return 0;
        else
            return -1;
    }
}
