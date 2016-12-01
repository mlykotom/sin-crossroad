package model;

import Map.CrossRoadPlus;
import Map.Road;
import Map.SpawnPoint;

import java.util.stream.Stream;


public class WorldSimple extends BaseWorld {
    public WorldSimple() {
        super(WorldSimple.class.getSimpleName());
        SpawnPoint spawnA = new SpawnPoint("SpawnA", 0, 0, 5000, 5001);
        SpawnPoint spawnB = new SpawnPoint("SpawnB", 5, 0, 99999990, 99999999);

        Road roadA = new Road(spawnA, spawnB);
        spawnA.setRoad(roadA);
        spawnB.setRoad(roadA);

        Stream.of(spawnA, spawnB).forEach(spawnPoint -> SpawnPoints.put(spawnPoint.getId(), spawnPoint));
        Stream.of(roadA).forEach(road -> Roads.put(road.getId(), road));
    }
}
