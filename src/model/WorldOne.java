package model;

import Map.CrossRoadPlus;
import Map.Road;
import Map.SpawnPoint;

import java.util.Arrays;
import java.util.stream.Stream;


public class WorldOne extends BaseWorld {
    public WorldOne() {
        super("WorldOne");
        SpawnPoint spawnA = new SpawnPoint("SpawnA", 1, 0);
        SpawnPoint spawnB = new SpawnPoint("SpawnB", 0, 1);
        SpawnPoint spawnC = new SpawnPoint("SpawnC", 1, 3);
        SpawnPoint spawnD = new SpawnPoint("SpawnD", 2, 1);

        CrossRoadPlus crossroad = new CrossRoadPlus("MainCrossroad", 1, 1);

        Road roadA = new Road(spawnA, crossroad);
        Road roadB = new Road(spawnB, crossroad);
        Road roadC = new Road(spawnC, crossroad);
        Road roadD = new Road(spawnD, crossroad);

        crossroad.setRoads(roadA, roadB, roadC, roadD);
        spawnA.setRoad(roadA);
        spawnB.setRoad(roadB);
        spawnC.setRoad(roadC);
        spawnD.setRoad(roadD);

        Stream.of(spawnA, spawnB, spawnC, spawnD).forEach(spawnPoint -> SpawnPoints.put(spawnPoint.getId(), spawnPoint));
        Stream.of(crossroad).forEach(crossRoad -> CrossRoads.put(crossRoad.getId(), crossRoad));
        Stream.of(roadA, roadB, roadC, roadD).forEach(road -> Roads.put(road.getId(), road));
    }
}
