package Behaviours.state;

import Map.CrossRoad;


public class CrossRoadStatus extends AgentStatus {
    public final CrossRoad crossRoad;


    public CrossRoadStatus(String agentId, CrossRoad crossRoad) {
        super(agentId);
        this.crossRoad = crossRoad;
    }
}
