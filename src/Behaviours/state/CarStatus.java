package Behaviours.state;

import Map.Place;
import Map.Road;
import com.sun.istack.internal.Nullable;

import java.util.List;
import java.util.UUID;


public class CarStatus extends AgentStatus {
    public final UUID currentRoadId;
    public final UUID sourcePlaceId;
    @Nullable public final UUID nextPlaceId;
    public final UUID destinationPlaceId;
    public final long timestampStart;
    public final long timestampEnd;


    public CarStatus(String agentId, List<Road> path, int currentRoad, Place origin, long timestampStart, long timestampEnd) {
        super(agentId);
        this.currentRoadId = path.get(currentRoad).getId();
        this.sourcePlaceId = origin.getId();
        this.nextPlaceId = (currentRoad + 1) >= path.size() ? null : path.get(currentRoad + 1).getId();
        this.destinationPlaceId = path.get(path.size() - 1).getId();
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
    }


//    public CarStatus(String agentId, Road currentRoad, @Nullable Place nextPlace, Place sourcePlace, Place destinationPlace, long timestampStart, long timestampEnd) {
//        super(agentId);
//        this.currentRoad = currentRoad;
//        this.sourcePlaceId = sourcePlace != null ? sourcePlace.getId() : null;  //TODO sometimes it's null
//        this.destinationPlaceId = destinationPlace != null ? destinationPlace.getId() : null;  //TODO sometimes it's null
//        this.timestampStart = timestampStart;
//        this.timestampEnd = timestampEnd;
//    }
}
