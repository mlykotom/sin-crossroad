package model;

import Map.CrossRoadPlus;
import Map.Road;
import Map.SpawnPoint;

import java.util.Arrays;
import java.util.stream.Stream;


public class WorldOne extends BaseWorld {
    public WorldOne() {
        super("WorldOne");
        SpawnPoint spawnA = new SpawnPoint("SpawnA", 1, 0,1000000,1000000);
        SpawnPoint spawnB = new SpawnPoint("SpawnB", 0, 1,1000000,1000000);
        SpawnPoint spawnC = new SpawnPoint("SpawnC", 1, 3,1000000,1000000);
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

        setupSpawnPoints(spawnA, spawnB, spawnC, spawnD);
        setupCrossRoads(crossroad);
        setupRoads(roadA, roadB, roadC, roadD);
    }


    @Override
    public int getGridSize() {
        return 4;
    }
}
