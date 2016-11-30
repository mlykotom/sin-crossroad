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
    private final SpawnPoint mSpawnPoint;

    public SpawnCarBehavior(WorldAgent a, long t, SpawnPoint s) {
        super(a, t);
        mSpawnPoint = s;
    }

    private static List<Road> createPath(SpawnPoint start) {
        List<Road> path = new LinkedList<>();

        Road currentRoad = start.Road(), nextRoad;
        Place currentPlace = start, nextPlace = currentRoad.nextPlace(currentPlace);

        path.add(start.Road());
        while (!(nextPlace instanceof SpawnPoint)) {
            // Get all connections at the next place
            List<Road> connections = currentRoad.nextPlace(currentPlace).Connections;

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
        sLogger.log(Level.INFO, "Spawning car at: " + mSpawnPoint.Name);
        Object[] args = {mSpawnPoint, createPath(mSpawnPoint)};
        WorldAgent worldAgent = (WorldAgent)myAgent;

        try {
            AgentController cont = worldAgent.getContainerController().createNewAgent(
                    CarAgent.getAgentName(worldAgent.getCarAgents().size()),
                    CarAgent.class.getName(),
                    args);
            worldAgent.getCarAgents().add(cont);   // TODO not proper because agents are destroying itself and then it lays "dirty" in the list
            cont.start();
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }
    }
}
