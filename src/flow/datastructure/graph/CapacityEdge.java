package flow.datastructure.graph;

/**
 * Created by chrisat on 27.10.16.
 */
public abstract class CapacityEdge<Vertex, Capacity> extends DirectedEdge<Vertex> {
    abstract Capacity getCapacity();
}
