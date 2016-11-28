package Map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


/**
 * Created by raven on 23.11.2016.
 */
public class World {
    public List<SpawnPoint> SpawnPoints = new ArrayList<>();
    public List<CrossRoad> CrossRoads = new ArrayList<>();
    public List<Road> Roads = new ArrayList<>();


    public World() {
        SpawnPoint spawnA = new SpawnPoint("SpawnA", 1, 0);
        SpawnPoint spawnB = new SpawnPoint("SpawnB", 0, 1);
        SpawnPoint spawnC = new SpawnPoint("SpawnC", 1, 2);
        SpawnPoint spawnD = new SpawnPoint("SpawnD", 2, 1);
        SpawnPoints.addAll(Arrays.asList(spawnA, spawnB, spawnC, spawnD));

        CrossRoadPlus crossroad = new CrossRoadPlus("MainCrossroad", 1, 1);
        CrossRoads.add(crossroad);

        Road roadA = new Road(spawnA, crossroad, 50); //TODO: Determine length from distance of 2 places
        Road roadB = new Road(spawnB, crossroad, 50); //TODO: Determine length from distance of 2 places
        Road roadC = new Road(spawnC, crossroad, 50); //TODO: Determine length from distance of 2 places
        Road roadD = new Road(spawnD, crossroad, 50); //TODO: Determine length from distance of 2 places
        Roads.addAll(Arrays.asList(roadA, roadB, roadC, roadD));

        crossroad.setRoads(roadA, roadB, roadC, roadD);
        spawnA.setRoad(roadA);
        spawnB.setRoad(roadB);
        spawnC.setRoad(roadC);
        spawnD.setRoad(roadD);
    }
}
