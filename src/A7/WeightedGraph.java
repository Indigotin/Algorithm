package A7;


import java.util.*;

public class WeightedGraph<V> extends AbstractGraph<V> {

  private List<PriorityQueue<WeightedEdge>> queues;

  public WeightedGraph(int[][] edges, V[] vertices) {
    super(edges, vertices);
    createQueues(edges, vertices.length);
  }

  public WeightedGraph(int[][] edges, int numberOfVertices) {
    super(edges, numberOfVertices);
    createQueues(edges, numberOfVertices);
  }

  public WeightedGraph(List<WeightedEdge> edges, List<V> vertices) {
    super((List)edges, vertices);
    createQueues(edges, vertices.size());
  }

  public WeightedGraph(List<WeightedEdge> edges,
      int numberOfVertices) {
    super((List)edges, numberOfVertices);
    createQueues(edges, numberOfVertices);
  }

  private void createQueues(int[][] edges, int numberOfVertices) {
    queues = new ArrayList<>();
    for (int i = 0; i < numberOfVertices; i++) {
      queues.add(new PriorityQueue<>()); // Create a queue
    }

    for (int i = 0; i < edges.length; i++) {
      int u = edges[i][0];
      int v = edges[i][1];
      int weight = edges[i][2];
      queues.get(u).offer(new WeightedEdge(u, v, weight));
    }
  }

  private void createQueues(List<WeightedEdge> edges,
      int numberOfVertices) {
    queues = new ArrayList<>();
    for (int i = 0; i < numberOfVertices; i++) {
      queues.add(new PriorityQueue<>()); // Create a queue
    }

    for (WeightedEdge edge: edges) {
      queues.get(edge.u).offer(edge); // Insert an edge into the queue
    }
  }

  /** Clone an array of queues */
  public List<PriorityQueue<WeightedEdge>> deepClone(
    List<PriorityQueue<WeightedEdge>> queues) {
    List<PriorityQueue<WeightedEdge>> copiedQueues =
      new ArrayList<>();

    for (int i = 0; i < queues.size(); i++) {
      copiedQueues.add(new PriorityQueue<WeightedEdge>());
      for (WeightedEdge e : queues.get(i)) {
        copiedQueues.get(i).add(e);
      }
    }

    return copiedQueues;
  }

  public List<PriorityQueue<WeightedEdge>> getWeightedEdges() {
    return queues;
  }

}