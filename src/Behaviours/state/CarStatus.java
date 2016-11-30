package Behaviours.state;

import Map.Place;
import Map.Road;


public class CarStatus extends AgentStatus {
    public final Road currentRoad;
    public final Place sourcePlace;
    public final Place destinationPlace;
    public final long timestampStart;
    public final long timestampEnd;


    public CarStatus(String agentId, Road currentRoad, Place sourcePlace, Place destinationPlace, long timestampStart, long timestampEnd) {
        super(agentId);
        this.currentRoad = currentRoad;
        this.sourcePlace = sourcePlace;
        this.destinationPlace = destinationPlace;
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
    }
}
