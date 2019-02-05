package flow.datastructure.graph;

/**
 * Created by chrisat on 27.10.16.
 */
public class FlowNetworkEdge<Vertex, Capacity> extends CapacityEdge<Vertex, Capacity> {
    private FlowStatus<Capacity> flowstatus;
    private FlowNetworkEdge<Vertex, Capacity> reverse;
    private Vertex to;
    private Vertex from;

    public FlowNetworkEdge(Vertex to, Vertex from) {
        this.to = to;
        this.from = from;
    }

    FlowStatus<Capacity> getFlowStatus() {
        return flowstatus;
    }

    public void setFlowstatus(FlowStatus<Capacity> flowstatus) {
        this.flowstatus = flowstatus;
    }

    public void setReverse(FlowNetworkEdge<Vertex, Capacity> edge) {
        this.reverse = edge;
    }

    public FlowNetworkEdge<Vertex, Capacity> getReverse() {
        return reverse;
    }

    @Override
    public Vertex getTo() {
        return to;
    }

    @Override
    public Vertex getFrom() {
        return from;
    }

    public FlowStatus<Capacity> getFlowstatus() {
        return flowstatus;
    }

    @Override
    public Capacity getCapacity() {
        return flowstatus.getCapacity();
    }

    public Capacity getFlow() {
        return flowstatus.getFlow();
    }

    public Integer residualFlow() { return (Integer)getCapacity()-(Integer)getFlow();}

    @Override
    public String toString() {
        return "From: " + from.toString() + ", To: " + to.toString();
    }
}
