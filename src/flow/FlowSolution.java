package flow;

import flow.datastructure.graph.Vertex;
import flow.flownetwork.FlowGraph;

import java.util.List;

/**
 * Created by chrisat on 28.10.16.
 */
public class FlowSolution {
    FlowGraph graph;
    int maximumFlow;
    int numberOfSteps;
    List<Vertex> sourceCut;

    FlowSolution(FlowGraph graph) {
        this.graph = graph;
    }

    public int getMaximumFlow() {
        return maximumFlow;
    }

    public int getNumberOfSteps() {
        return numberOfSteps;
    }

    public List<Vertex> getSourceCut() {
        return sourceCut;
    }

    public FlowGraph getGraph() {
        return graph;
    }

    public void setMaximumFlow(int maximumFlow) {
        this.maximumFlow = maximumFlow;
    }

    public void setNumberOfSteps(int numberOfSteps) {
        this.numberOfSteps = numberOfSteps;
    }

    public void setSourceCut(List<Vertex> sourceCut) {
        this.sourceCut = sourceCut;
    }

    public void setGraph(FlowGraph graph) {
        this.graph = graph;
    }

    @Override
    public String toString() {
        return getMaximumFlow() + "\n"
                + getGraph().toString() + "\n"
                + getSourceCut().toString().replace("[", "")
                                           .replace(", ", " ")
                                           .replace("]", "") + "\n"
                + + getNumberOfSteps() + "\n";
    }
}
