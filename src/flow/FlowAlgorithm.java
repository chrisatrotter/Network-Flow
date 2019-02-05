package flow;

import flow.flownetwork.FlowGraph;

/**
 * Created by chrisat on 28.10.16.
 */
public interface FlowAlgorithm {
    FlowSolution search(FlowGraph graph);

}
