package flow;

import flow.datastructure.graph.FlowNetworkEdge;
import flow.datastructure.graph.Vertex;
import flow.flownetwork.FlowGraph;
import flow.searchalgorithm.SearchAlgorithm;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparing;

/**
 * Created by chrisat on 28.10.16.
 */
public class EdmondKarp implements FlowAlgorithm{
    SearchAlgorithm pathFinder;

    EdmondKarp(SearchAlgorithm pathFinder) {
        this.pathFinder = pathFinder;
    }

    public List<FlowNetworkEdge> augmentPath(FlowGraph graph, Vertex source, Vertex sink) {
        return pathFinder.findPath(graph, source, sink, true);
    }
    @Override
    public FlowSolution search(FlowGraph graph) {
        return calc(graph, graph.getSource(), graph.getSink());
    }

    public FlowSolution calc(FlowGraph graph, Vertex source, Vertex sink) {
        FlowSolution solution = new FlowSolution(graph);

        while(true) {
            Integer minimum = Integer.MAX_VALUE;
            List<FlowNetworkEdge> path = augmentPath(graph, source, sink);
            if(path.isEmpty()) break;

            for(FlowNetworkEdge edge : path)
                minimum = Math.min(edge.residualFlow(), minimum);

            Vertex vertex = sink;
            while(vertex != source) {
                for(FlowNetworkEdge edge : vertex.getInEdges()) {
                    if(!path.contains(edge)) continue;
                    edge.getFlowstatus().setFlow((Integer)edge.getFlow()+minimum);
                    vertex = (Vertex) edge.getFrom();
                }
            }
            solution.setNumberOfSteps(solution.getNumberOfSteps()+1);
        }

        solution.setMaximumFlow((Integer)source.getOutEdges().get(0).getFlow());
        solution.setGraph(graph);
        solution.setSourceCut(pathFinder.findPath(graph, source, sink, false).stream()
                                                                             .map(edge -> (Vertex)edge.getTo())
                                                                             .sorted(comparing(Vertex::getVertex))
                                                                             .collect(Collectors.toList()));
        return solution;
    }
}
