package model;

import Map.CrossRoad;
import Map.Road;
import Map.SpawnPoint;

import java.util.*;
import java.util.stream.Stream;


public abstract class BaseWorld {
    public Map<UUID, SpawnPoint> SpawnPoints = new LinkedHashMap<UUID, SpawnPoint>();
    public Map<UUID, CrossRoad> CrossRoads = new LinkedHashMap<UUID, CrossRoad>();
    public Map<UUID, Road> Roads = new LinkedHashMap<UUID, Road>();
    public final String name;


    public abstract int getGridSize();


    public BaseWorld(String name) {
        this.name = name;
    }


    protected void setupCrossRoads(CrossRoad... places) {
        Stream.of(places).forEach(crossRoad -> CrossRoads.put(crossRoad.getId(), crossRoad));
    }


    protected void setupRoads(Road... places) {
        Stream.of(places).forEach(crossRoad -> Roads.put(crossRoad.getId(), crossRoad));
    }


    protected void setupSpawnPoints(SpawnPoint... places) {
        Stream.of(places).forEach(crossRoad -> SpawnPoints.put(crossRoad.getId(), crossRoad));
    }
}
