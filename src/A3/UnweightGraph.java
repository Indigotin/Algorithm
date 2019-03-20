package A3;


import A7.AbstractGraph;

import java.util.List;

public class UnweightGraph<V> extends AbstractGraph<V> {
    public UnweightGraph(int[][] edges, V[] vertices) {
        super(edges, vertices);
    }

    public UnweightGraph(List<Edges> edges, List<V> vertices) {
        super(edges, vertices);
    }

    public UnweightGraph(List<Edges> edges, int numberOfVertices) {
        super(edges, numberOfVertices);
    }

    public UnweightGraph(int[][] edges, int numberOfVertices) {
        super(edges, numberOfVertices);
    }
}
