package model;

import Map.CrossRoad;
import Map.Road;
import Map.SpawnPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public abstract class BaseWorld {
    public List<SpawnPoint> SpawnPoints = new ArrayList<>();
    public List<CrossRoad> CrossRoads = new ArrayList<>();
    public HashMap<UUID, Road> Roads = new HashMap<>();
    public final String name;


    public BaseWorld(String name) {
        this.name = name;
    }
}
