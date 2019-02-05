package flow;

import flow.flownetwork.ResidualGraph;

/**
 * Created by chrisat on 28.10.16.
 */
public class FlowSolver {
    private FlowAlgorithm algorithm;
    private ResidualGraph residualGraph;
    private int[][] graph;

    public FlowSolver(ResidualGraph residualGraph, FlowAlgorithm algorithm, int[][] graph) {
        this.residualGraph = residualGraph;
        this.algorithm = algorithm;
        this.graph = graph;
    }

    public FlowSolution result() {
        return algorithm.search(residualGraph);
    }

}
