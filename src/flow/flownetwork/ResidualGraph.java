package flow.flownetwork;

import flow.datastructure.graph.FlowNetworkEdge;

import java.util.List;

/**
 * Created by chrisat on 28.10.16.
 */
public class ResidualGraph extends FlowGraph {

    public void augmentPath(List<FlowNetworkEdge> path, Integer augment) {
        path.stream().forEach(edge -> edge.getFlowstatus().setFlow((Integer)edge.getFlow()+augment));
    }
}
