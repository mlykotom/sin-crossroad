package model;

import Map.CrossRoadPlus;
import Map.Place;
import Map.Road;
import Map.SpawnPoint;


/**
 * Created by adamj on 03.12.2016.
 */
public class WorldThree extends BaseWorld {
    public WorldThree() {
        super("WorldThree");
        SpawnPoint spawnA = new SpawnPoint("SpawnA", 1, 0);
        SpawnPoint spawnB = new SpawnPoint("SpawnB", 0, 1);
        SpawnPoint spawnC = new SpawnPoint("SpawnC", 1, 4);
        SpawnPoint spawnF = new SpawnPoint("SpawnF", 0, 3);
        SpawnPoint spawnG = new SpawnPoint("spawnG", 5, 3);
        SpawnPoint spawnH = new SpawnPoint("spawnH", 4, 5);

        Place placeD = new Place("placeD", 4, 1);
        //SpawnPoint spawnJ = new SpawnPoint("spawnJ", 4, 1);
        //SpawnPoint spawnK = new SpawnPoint("spawnK", 4, 2);


        CrossRoadPlus mainCrossroad = new CrossRoadPlus("MainCrossroad", 1, 1);

        Road roadA = new Road(spawnA, mainCrossroad);
        Road roadB = new Road(spawnB, mainCrossroad);
        Road roadD = new Road(placeD, mainCrossroad);

        CrossRoadPlus minorCrossroad = new CrossRoadPlus("MinorCrossroad", 1, 3);

        Road roadC = new Road(mainCrossroad, minorCrossroad);
        Road roadE = new Road(spawnC, minorCrossroad);
        Road roadG = new Road(spawnF, minorCrossroad);

        CrossRoadPlus thirdCrossroad = new CrossRoadPlus("ThirdCrossroad", 4, 3);


        Road roadF = new Road(thirdCrossroad, minorCrossroad);
        Road roadH = new Road(spawnG, thirdCrossroad);
        Road roadI = new Road(spawnH, thirdCrossroad);
        Road roadJ = new Road(placeD, thirdCrossroad);

        placeD.addRoads(roadJ, roadD);

        mainCrossroad.setRoads(roadA, roadB, roadC, roadD);
        minorCrossroad.setRoads(roadC, roadE, roadF, roadG);
        thirdCrossroad.setRoads(roadF, roadH, roadI, roadJ);

        spawnA.setRoad(roadA);
        spawnB.setRoad(roadB);
        spawnC.setRoad(roadE);
        spawnF.setRoad(roadG);
        spawnG.setRoad(roadH);
        spawnH.setRoad(roadI);

        setupSpawnPoints(spawnA, spawnB, spawnC, spawnG, spawnH, spawnF);
        setupCrossRoads(mainCrossroad, minorCrossroad, thirdCrossroad);

        setupRoads(roadA, roadB, roadC, roadD, roadE, roadF, roadG, roadH, roadI, roadJ);
    }


    @Override
    public int getGridSize() {
        return 8;
    }
}