package Behaviours.state;

import Map.SpawnPoint;

import java.util.UUID;


public class SpawnPointStatus extends AgentStatus {
    public final UUID spawnPointId;
    public final long producedCars;


    public SpawnPointStatus(String agentID, SpawnPoint place, long producedCars) {
        super(agentID);
        this.spawnPointId = place.getId();
        this.producedCars = producedCars;
    }
}
