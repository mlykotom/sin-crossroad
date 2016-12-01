package Behaviours.state;

import Map.Place;
import Map.Road;

import java.util.List;
import java.util.UUID;


public class CarStatus extends AgentStatus {
    public final UUID currentRoadId;
    public final UUID sourcePlaceId;
    public final UUID destinationPlaceId;
    public final long timestampStart;
    public final long timestampEnd;
    public final boolean isEntered;


    public CarStatus(String agentId, List<Road> path, int currentRoad, Place origin, boolean entered, long timestampStart, long timestampEnd) {
        super(agentId);
        this.currentRoadId = path.get(currentRoad).getId();
        this.sourcePlaceId = origin.getId();
        this.destinationPlaceId = path.get(path.size() - 1).getId();
        this.timestampStart = timestampStart;
        this.isEntered = entered;
        this.timestampEnd = timestampEnd;
    }


    public CarStatus(String agentId, Road currentRoad, Place sourcePlace, Place destinationPlace, long timestampStart, long timestampEnd, boolean isEntered) {
        super(agentId);
        this.currentRoad = currentRoad;
        this.sourcePlaceId = sourcePlace.getId();
        this.destinationPlaceId = destinationPlace.getId();
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
        this.isEntered = isEntered;
    }
}