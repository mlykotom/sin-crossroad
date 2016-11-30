package model;

import Map.CrossRoad;
import Map.Road;
import Map.SpawnPoint;

import java.util.*;


public abstract class BaseWorld {
    public Map<UUID, SpawnPoint> SpawnPoints = new LinkedHashMap<UUID, SpawnPoint>();
    public Map<UUID, CrossRoad> CrossRoads = new LinkedHashMap<UUID, CrossRoad>();
    public Map<UUID, Road> Roads = new LinkedHashMap<UUID, Road>();
    public final String name;


    public BaseWorld(String name) {
        this.name = name;
    }
}
