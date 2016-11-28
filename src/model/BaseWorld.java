package model;

import Map.CrossRoad;
import Map.Road;
import Map.SpawnPoint;

import java.util.ArrayList;
import java.util.List;


public abstract class BaseWorld {
    public List<SpawnPoint> SpawnPoints = new ArrayList<>();
    public List<CrossRoad> CrossRoads = new ArrayList<>();
    public List<Road> Roads = new ArrayList<>();
}
