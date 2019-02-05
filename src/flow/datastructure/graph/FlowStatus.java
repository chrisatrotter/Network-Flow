package flow.datastructure.graph;

/**
 * Created by chrisat on 27.10.16.
 */
public class FlowStatus<Capacity> {
    public Capacity capacity;
    public Capacity flow;

    public FlowStatus(Capacity capacity, Capacity flow) {
        this.capacity = capacity;
        this.flow = flow;
    }

    public void setCapacity(Capacity capacity) {
        this.capacity = capacity;
    }

    public void setFlow(Capacity flow) {
        this.flow = flow;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public Capacity getFlow() {
        return flow;
    }

    @Override
    public String toString() {
        return flow + " / " + capacity + ", tp: " + ((Integer)capacity-(Integer)flow);
    }
}
