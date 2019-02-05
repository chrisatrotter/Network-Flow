package flow.datastructure.graph;

/**
 * Created by chrisat on 27.10.16.
 */
public abstract class DirectedEdge<Vertex> {
    abstract Vertex getTo();
    abstract Vertex getFrom();
}
