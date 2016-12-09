package model;

import Map.CrossRoadPlus;
import Map.Road;
import Map.SpawnPoint;


/**
 * Created by adamj on 03.12.2016.
 */
public class WorldTwo extends BaseWorld {
    public WorldTwo() {
        super("WorldTwo");
        SpawnPoint spawnA = new SpawnPoint("SpawnA", 1, 0);
        SpawnPoint spawnB = new SpawnPoint("SpawnB", 0, 1);
        SpawnPoint spawnD = new SpawnPoint("SpawnD", 2, 1);

        SpawnPoint spawnC = new SpawnPoint("SpawnC", 1, 4);
        SpawnPoint spawnE = new SpawnPoint("SpawnE", 4, 3);
        SpawnPoint spawnF = new SpawnPoint("SpawnF", 0, 3);

        CrossRoadPlus mainCrossroad = new CrossRoadPlus("MainCrossroad", 1, 1);

        Road roadA = new Road(spawnA, mainCrossroad);
        Road roadB = new Road(spawnB, mainCrossroad);
        Road roadD = new Road(spawnD, mainCrossroad);

        CrossRoadPlus minorCrossroad = new CrossRoadPlus("MinorCrossroad", 1, 3);

        Road roadC = new Road(mainCrossroad, minorCrossroad);
        Road roadE = new Road(spawnC, minorCrossroad);
        Road roadF = new Road(spawnE, minorCrossroad);
        Road roadG = new Road(spawnF, minorCrossroad);


        mainCrossroad.setRoads(roadB, roadC, roadD, roadA);
        minorCrossroad.setRoads(roadG, roadE, roadF, roadC);

        spawnA.setRoad(roadA);
        spawnB.setRoad(roadB);
        spawnD.setRoad(roadD);

        spawnC.setRoad(roadE);
        spawnE.setRoad(roadF);
        spawnF.setRoad(roadG);

        setupSpawnPoints(spawnA, spawnB, spawnC, spawnD, spawnE, spawnF);
        setupCrossRoads(mainCrossroad, minorCrossroad);

        setupRoads(roadA, roadB, roadC, roadD, roadE, roadF, roadG);
    }


    @Override
    public int getGridSize() {
        return 8;
    }
}