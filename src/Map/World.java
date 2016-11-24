package Map;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by raven on 23.11.2016.
 */
public class World {
    public List<SpawnPoint> SpawnPoints;
    public List<CrossRoad> CrossRoads;
    public List<Road> Roads;

    public World()
    {
        SpawnPoints = new LinkedList<>();
        SpawnPoint spawnA = new SpawnPoint("SpawnA");
        SpawnPoint spawnB = new SpawnPoint("SpawnB");
        SpawnPoint spawnC = new SpawnPoint("SpawnC");
        SpawnPoint spawnD = new SpawnPoint("SpawnD");
        SpawnPoints.add(spawnA);
        SpawnPoints.add(spawnB);
        SpawnPoints.add(spawnC);
        SpawnPoints.add(spawnD);

        CrossRoads = new LinkedList<>();
        CrossRoadPlus crossroad = new CrossRoadPlus("MainCrossroad");
        CrossRoads.add(crossroad);

        Roads = new LinkedList<>();
        Road roadA = new Road(spawnA, crossroad);
        Road roadB = new Road(spawnB, crossroad);
        Road roadC = new Road(spawnC, crossroad);
        Road roadD = new Road(spawnD, crossroad);
        Roads.add(roadA);
        Roads.add(roadB);
        Roads.add(roadC);
        Roads.add(roadD);

        crossroad.SetExits(roadA, roadB, roadC, roadD);
        spawnA.SetRoad(roadA);
        spawnB.SetRoad(roadB);
        spawnC.SetRoad(roadC);
        spawnD.SetRoad(roadD);
    }
}
