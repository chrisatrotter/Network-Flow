package flow.flownetwork;

import flow.datastructure.graph.FlowNetworkEdge;
import flow.datastructure.graph.FlowStatus;
import flow.datastructure.graph.Graph;
import flow.datastructure.graph.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by chrisat on 28.10.16.
 */
public class FlowGraph implements Graph {
    private Vertex source, sink;
    private List<Vertex> internal;

    public FlowGraph() {
        internal = new ArrayList<>();
    }

    /**
     * Construct the vertices, edges, set source and sink of the graph and check if it upholds
     * the flow graph constraints.
     * Notice: Nodes with symmetrical edges, as in (x -> y) and (y -> x), know about their opposite.
     * edge: (x -> y) has the reverse (y -> x) and vice versa.
     * @param graph - the graph draw as a double array.
     */
    public void createGraph(int[][] graph) {
        source = new Vertex(0);
        sink = new Vertex(graph.length+1);
        createVertices(graph.length+1);
        createEdges(graph);
        verifyFlowGraph();
        setSource();
        setSink();
        setReverse();
    }

    /**
     * Create all the vertices of the graph with a id equal to the incremental value.
     * @param numberOfVertices - the number of vertices to be created.
     */
    private void createVertices(int numberOfVertices) {
        IntStream.range(1, numberOfVertices).forEach(i -> internal.add(new Vertex(i)));
    }

    /**
     * Create edges of the graph from the two dimensional-array.
     * Continue if value in the graph is 0. No edge exists between the two vertices,
     * else call createEdges method creating a flow network edge from vertex 'i' to vertex 'j'.
     * @param graph - the two dimensional-array, vertical('j') = to, horizontal('i') = from.
     */
    private void createEdges(int[][] graph) {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (graph[i][j] == 0) continue;
                createEdges(getVertices().get(j), getVertices().get(i), graph[i][j], 0);
            }
        }
    }

    /**
     * Create a flow network edge from a vertex to another with a capacity and flow.
     * @param to - Destination.
     * @param from - Source.
     * @param capacity - Upper bound of the amount of flow the edge can handle.
     * @param flow - incoming amount of flow into the edge.(default = 0).
     */
    private void createEdges(Vertex to, Vertex from, int capacity, int flow) {
        FlowNetworkEdge flowedge = new FlowNetworkEdge<Vertex, Integer>(to, from);
        flowedge.setFlowstatus(new FlowStatus(capacity, flow));
        to.setInEdges(addEdge(to, to.getInEdges(), flowedge));
        from.setOutEdges(addEdge(from, from.getOutEdges(), flowedge));
    }

    public List<FlowNetworkEdge> addEdge(Vertex v, List<FlowNetworkEdge> edgeList, FlowNetworkEdge edge) {
        edgeList.add(edge);
        return edgeList;
    }

    private void printEdge(Vertex to, Vertex from, FlowNetworkEdge flowedge, int capacity, int flow) {
        System.out.println("From: " + from.getVertex());
        System.out.println("To: " + to.getVertex() + ", Indegree: " + to.getIndegree());
        System.out.println("Capacity: " + capacity);
        System.out.println("Edge values: ");
        System.out.println("\tTo: " + flowedge.getTo().toString());
        System.out.println("\tFrom: " + flowedge.getFrom().toString());
        System.out.println("\tCapacity: " + flowedge.getFlowstatus());
        System.out.println("\tReverseEdge: " + flowedge.getReverse());
    }

    /**
     * Conditions for a valid flow.
     * 1. The flow must have at least one sink and source.
     *    If it has multiple sinks or sources, then create a single artificial sink or source.
     * 2. The flow is conserved, meaning all that goes in must also go out.
     * 3. The flow can not exceed the given capacity, meaning it can be less or equal to the capacity.
     *    flow <= capacity.
     */
    private void verifyFlowGraph() {
        if (!hasSink() && !hasSource()) throw new IllegalArgumentException("No source or sink found");
        if (!isConserved()) throw new IllegalArgumentException("Conservation condition is not maintained in the flow graph. Fin(u)=Fout(u) for internal u");
        if (!isCapacityLimit()) throw new IllegalArgumentException("Does not satisfy the capacity constraint, flow > capacity");
    }

    /**
     * Checking that the flow is within the capacity limits meeting the feasibility constraint.
     * @return true - if the flow <= capacity, else false(constraint breached)
     */
    private boolean isCapacityLimit() {
        for (Vertex vertex : internal) {
            long overCapacitance = vertex.getOutEdges().stream()
                                                       .filter(i -> (Integer)i.getCapacity() < (Integer)i.getFlow())
                                                       .count();
            if (overCapacitance > 0) return false;
        }
        return true;
    }

    /**
     * Checking if the flow conservation, meaning all flow that goes in must also go out.
     * No vertex has the ability to consume flow along the way. All flow must stream from
     * the sink to the source.
     * If the total income differs from the total outgoing, then the conservation constraint is breached.
     * @return true - if the flow is conserved, else false.
     */
    private boolean isConserved() {
        for (Vertex vertex: internal) {
            if (vertex.getIndegree() == 0 || vertex.getOutdegree() == 0) continue;
            int in = vertex.getInEdges().stream()
                                        .filter(i -> vertex == i.getFrom())
                                        .mapToInt(i -> (Integer) i.getFlow())
                                        .sum();
            int out = vertex.getInEdges().stream()
                                         .filter(i -> vertex == i.getTo())
                                         .mapToInt(i -> (Integer) i.getFlow())
                                         .sum();
            if (out != in) return false;
        }
        return true;
    }

    /**
     * Checking if the flow has a Sink, meaning a vertex with no edges going outward.
     * @return true - if there is a sink, else false.
     */
    private boolean hasSink() {
        return 0 < internal.stream()
                           .filter(i -> i.getOutdegree() == 0)
                           .count();
    }

    /**
     * Checking if the flow has a Source, meaning a vertex with no edges pointing to it.
     * @return true - if there is a source, else false.
     */
    private boolean hasSource() {
        return 0 < internal.stream()
                           .filter(i -> i.getOutdegree() == 0)
                           .count();
    }

    /**
     * If one or more sources exists, creates a flow network edge for each source going from the artificial source
     * to the other existing sources. The capacity of each source is preserved in each newly created flow network edge.
     */
    private void setSource() {
        List<Vertex> sources = getVertices().stream()
                .filter(i -> i.getIndegree() == 0)
                .collect(Collectors.toList());

        for (Vertex to : sources) {
            int capacity = to.getOutEdges().stream()
                                        .mapToInt(i -> (Integer) i.getCapacity())
                                        .sum();

            createEdges(to, getSource(), capacity, 0);
        }
    }

    /**
     * If one or more sinks exists, creates a flow network edge for each sink going from the artificial sink
     * to the other existing sinks. The capacity of each sink is preserved in each newly created flow network edge.
     */
    private void setSink() {
        List<Vertex> sinks = getVertices().stream()
                                     .filter(i -> i.getOutdegree() == 0)
                                     .collect(Collectors.toList());

        for (Vertex from : sinks) {
            int capacity = from.getInEdges().stream()
                                            .mapToInt(i -> (Integer) i.getCapacity())
                                            .sum();

            createEdges(getSink(), from, capacity, 0);
        }
    }

    private void setReverse() {
        for (Vertex vertex : getVertices()) {
            vertex.getOutEdges().stream()
                                .filter(edge -> hasReverseEdge((Vertex)edge.getTo(), vertex.getInEdges()))
                                .forEach(edge -> updateReverse((Vertex)edge.getTo(), vertex, edge));
        }
    }

    public void updateReverse(Vertex to, Vertex from, FlowNetworkEdge flowedge) {
        from.getInEdges().stream()
                         .filter(edge -> edge.getFrom() == to)
                         .forEach(edge -> flowedge.setReverse(edge));
    }

    private boolean hasReverseEdge(Vertex to, List<FlowNetworkEdge> edgeList) {
        return edgeList.stream().anyMatch(edge -> edge.getFrom() == to);
    }

    /**
     * @return collection of the internal vertices.
     */
    @Override
    public List<Vertex> getVertices() {
        return internal;
    }

    /**
     * @return the sink.
     */
    public Vertex getSink() {
        return sink;
    }

    /**
     * @return the source
     */
    public Vertex getSource() {
        return source;
    }

    /**
     * Note: Try to figure out the use of higher-order procedures/functions to send as parameter argument.
     *       Then you can send edge.capacity() as one function and edge.getFlow() as another instead of
     *       duplicating code. Both functions do the same, but the function changes slightly by function.
     * @return 2D array of the flow within the graph.
     */
    private int[][] convertFlowToArray() {
        int[][] graph = new int[getVertices().size()][getVertices().size()];

        for (Vertex vertex : getVertices()) {
            List<FlowNetworkEdge> edges = vertex.getOutEdges().stream()
                                                .filter(edge -> edge.getFrom() != getSource())
                                                .filter(edge -> edge.getTo() != getSink())
                                                .collect(Collectors.toList());

            /**
             * Find a way to assign variables while iterating streams.
             */
            for (FlowNetworkEdge edge : edges) {
                int from = ((Vertex)edge.getFrom()).getVertex()-1;
                int to = ((Vertex)edge.getTo()).getVertex()-1;
                graph[from][to] = (Integer)edge.getFlow();
            }
        }
        return graph;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(convertFlowToArray())
                .replace(",", "")
                .replace(" [", "")
                .replace("[", "")
                .replace("]]", "")
                .replace("]", "\n")
                .replace("], ", "\n");
    }
}
