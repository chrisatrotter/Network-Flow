package flow.searchalgorithm;

import flow.datastructure.graph.FlowNetworkEdge;
import flow.datastructure.graph.Vertex;
import flow.flownetwork.FlowGraph;

import java.util.*;

/**
 * Created by chrisat on 28.10.16.
 */
public class BreadthFirst implements SearchAlgorithm {
    @Override
    public List<FlowNetworkEdge> findPath(FlowGraph graph, Vertex source, Vertex sink, boolean search) {
        Set<Vertex> visited = new HashSet<>();
        List<FlowNetworkEdge> parent = new ArrayList<>();
        boolean pathFound = false;

        LinkedList<Vertex> queue = new LinkedList<>();

        queue.add(source);
        visited.add(source);

        while (!queue.isEmpty()) {
            Vertex current = queue.poll();
            for (FlowNetworkEdge edge : current.getOutEdges()) {
                Vertex vertex = ((Vertex) edge.getTo());
                if (visited.contains(vertex) || edge.residualFlow() <= 0) continue;
                parent.add(edge);
                visited.add(vertex);
                queue.add(vertex);
                if (vertex == sink) {
                    pathFound = true;
                    break;
                }
            }
        }
        if(search && !pathFound) parent.clear();
        return parent;
    }
}
