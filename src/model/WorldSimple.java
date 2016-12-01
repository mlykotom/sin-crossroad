package model;

import Map.CrossRoadPlus;
import Map.Road;
import Map.SpawnPoint;

import java.util.stream.Stream;


public class WorldSimple extends BaseWorld {
    public WorldSimple() {
        super(WorldSimple.class.getSimpleName());
        SpawnPoint spawnA = new SpawnPoint("SpawnA", 0, 0);
        SpawnPoint spawnB = new SpawnPoint("SpawnB", 2, 0);


        Road roadA = new Road(spawnA, spawnB);

        spawnA.setRoad(roadA);
        spawnB.setRoad(roadA);

        setupSpawnPoints(spawnA, spawnB);
//        setupCrossRoads(crossroad);
        setupRoads(roadA);
    }


    @Override
    public int getGridSize() {
        return 3;
    }
}
