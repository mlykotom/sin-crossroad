package Behaviours.world;

import Agents.CarAgent;
import Agents.SpawnPointAgent;
import Agents.WorldAgent;
import Map.Place;
import Map.Road;
import Map.SpawnPoint;
import jade.core.behaviours.TickerBehaviour;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;


public class SpawnCarBehavior extends TickerBehaviour {
    private static Logger sLogger = Logger.getMyLogger(SpawnCarBehavior.class.getSimpleName());
    private final SpawnPoint mSpawnPoint;
    private final SpawnPointAgent mSpawnAgent;
    private Random mRandom;
    private int mMaxSpawnDelay, mMinSpawnDelay;


    /**
     * @param a Agent running this behaviour.
     * @param s Spawn point from which the cars will originate.
     */
    public SpawnCarBehavior(SpawnPointAgent a, SpawnPoint s) {
        super(a, Integer.MAX_VALUE);
        mSpawnAgent = a;
        mRandom = new Random();
        mSpawnPoint = s;
        mMinSpawnDelay = s.getMinSpawnDelay();
        mMaxSpawnDelay = s.getMaxSpawnDelay();

        planNextSpawn();
    }


    private static List<Road> createPath(SpawnPoint start) {
        List<Road> path = new LinkedList<>();

        Road currentRoad = start.getRoad(), nextRoad;
        Place currentPlace = start, nextPlace = currentRoad.nextPlace(currentPlace);

        path.add(start.getRoad());

        while (!(nextPlace instanceof SpawnPoint)) {
            // Get all connections at the next place
            List<Road> connections = nextPlace.getRoads();

            // Pick randomly one connection that's not the same as current road
            nextRoad = currentRoad;
            while (currentRoad == nextRoad) {
                nextRoad = connections.get(ThreadLocalRandom.current().nextInt(0, connections.size()));
            }

            path.add(nextRoad);
            currentRoad = nextRoad;
            currentPlace = nextPlace;
            nextPlace = currentRoad.nextPlace(currentPlace);
        }

        return path;
    }


    private void planNextSpawn() {
        int range = mMaxSpawnDelay - mMinSpawnDelay;
        int randomDelay = mMinSpawnDelay;
        if (range > 0) randomDelay += mRandom.nextInt(range);

        reset(randomDelay);
    }


    @Override
    protected void onTick() {
        sLogger.log(Level.INFO, "Spawning car at: " + mSpawnPoint.getName());
        setupCarAgent();
        planNextSpawn();
    }


    private void setupCarAgent() {
        Object[] args = {mSpawnPoint, createPath(mSpawnPoint)};

        try {
            AgentController cont = myAgent.getContainerController().createNewAgent(
                    CarAgent.getAgentName(WorldAgent.sInstance.getCarAgentNewId()),
                    CarAgent.class.getName(),
                    args);
            cont.start();
            mSpawnAgent.addProducedCar();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
