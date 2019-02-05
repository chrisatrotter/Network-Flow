package flow.datastructure.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chrisat on 28.10.16.
 */
public class Vertex {
    private int vertex;
    private List<FlowNetworkEdge> inEdges;
    private List<FlowNetworkEdge> outEdges;

    public Vertex(int vertex) {
        this.vertex = vertex;
        inEdges = new ArrayList<>();
        outEdges = new ArrayList<>();
    }

    public void setInEdges(List<FlowNetworkEdge> inEdges) {
        this.inEdges = inEdges;
    }

    public void setOutEdges(List<FlowNetworkEdge> outEdges) {
        this.outEdges = outEdges;
    }

    public int getIndegree() {
        return inEdges.size();
    }

    public int getOutdegree() {
        return outEdges.size();
    }

    public List<FlowNetworkEdge> getInEdges() {
        return inEdges;
    }

    public List<FlowNetworkEdge> getOutEdges() {
        return outEdges;
    }

    public int getVertex() {
        return vertex;
    }

    @Override
    public String toString() {
        return "" + vertex;
    }
}
