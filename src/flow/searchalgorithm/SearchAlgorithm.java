package flow.searchalgorithm;

import flow.datastructure.graph.FlowNetworkEdge;
import flow.datastructure.graph.Vertex;
import flow.flownetwork.FlowGraph;

import java.util.List;

/**
 * Created by chrisat on 28.10.16.
 */
public interface SearchAlgorithm {

    public List<FlowNetworkEdge> findPath(FlowGraph graph, Vertex source, Vertex sink, boolean search);

    /*
    public boolean markPath(Vertex source, Vertex sink);
    public List<FlowNetworkEdge> getPath(Vertex sink);
    */

}
