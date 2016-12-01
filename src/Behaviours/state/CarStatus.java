package Behaviours.state;

import Map.Place;
import Map.Road;

import java.util.UUID;


public class CarStatus extends AgentStatus {
    public final Road currentRoad;
    public final UUID sourcePlaceId;
    public final UUID destinationPlaceId;
    public final long timestampStart;
    public final long timestampEnd;


    public CarStatus(String agentId, Road currentRoad, Place sourcePlace, Place destinationPlace, long timestampStart, long timestampEnd) {
        super(agentId);
        this.currentRoad = currentRoad;
        this.sourcePlaceId = sourcePlace != null ? sourcePlace.getId() : null;  //TODO sometimes it's null
        this.destinationPlaceId = destinationPlace != null ? destinationPlace.getId() : null;  //TODO sometimes it's null
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
    }
}
