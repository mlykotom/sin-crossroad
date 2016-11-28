package Behaviours.world;

import Agents.CarAgent;
import Agents.WorldAgent;
import Map.Place;
import Map.Road;
import Map.SpawnPoint;
import jade.core.behaviours.TickerBehaviour;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;


public class SpawnCarBehavior extends TickerBehaviour {
    private static Logger sLogger = Logger.getMyLogger(SpawnCarBehavior.class.getSimpleName());
    private final WorldAgent mWorldAgent;
    private final SpawnPoint mSpawnPoint;


    public SpawnCarBehavior(WorldAgent a, long t, SpawnPoint s) {
        super(a, t);
        mWorldAgent = a;
        mSpawnPoint = s;
    }


    private static List<Road> createPath(SpawnPoint start) {
        List<Road> path = new LinkedList<>();

        Road currentRoad = start.getRoad(), nextRoad;
        Place currentPlace = start, nextPlace = currentRoad.nextPlace(currentPlace);

        path.add(start.getRoad());
        while (!(nextPlace instanceof SpawnPoint)) {
            // Get all connections at the next place
            List<Road> connections = currentRoad.nextPlace(currentPlace).getRoads();

            // Pick randomly one connection that's not the same as current road
            nextRoad = currentRoad;
            while (currentRoad == nextRoad) {
                nextRoad = connections.get(ThreadLocalRandom.current().nextInt(0, connections.size()));
            }

            path.add(nextRoad);
            currentRoad = nextRoad;
            nextPlace = currentRoad.nextPlace(currentPlace);
        }

        return path;
    }


    @Override
    protected void onTick() {
        sLogger.log(Level.INFO, "Spawning car at: " + mSpawnPoint.getName());

        Object[] args = {mSpawnPoint, createPath(mSpawnPoint)};

        try {
            AgentController cont = mWorldAgent.getContainerController().createNewAgent(
                    CarAgent.getAgentName(mWorldAgent.getCarAgents().size()),
                    CarAgent.class.getName(),
                    args);
            mWorldAgent.getCarAgents().add(cont);   // TODO not proper because agents are destroying itself and then it lays "dirty" in the list
            cont.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
