package Agents;

import GUI.MainGui;
import Map.*;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.concurrent.ThreadLocalRandom;

import java.util.*;
import java.util.logging.Level;

/**
 * Created by raven on 23.11.2016.
 */
public class WorldAgent extends Agent {
    private Logger myLogger = Logger.getMyLogger(getClass().getName());

    private World _world;
    private AgentController _ping;
    private AgentController _test;
    private List<AgentController> _crossRoadAgents;
    private List<AgentController> _carAgents;

    private MainGui _gui;
    private int _updatePeriod = 100;

    protected void setup()
    {
        myLogger.log(Level.INFO, "Creating World");
        _world = new World();
        _gui = new MainGui();

        ContainerController controller = getContainerController();
        _crossRoadAgents = new LinkedList<>();
        _carAgents = new LinkedList<>();
        try {
            for(CrossRoad crossRoad : _world.CrossRoads)
            {
                Object[] args = {crossRoad};
                AgentController cont = controller.createNewAgent(crossRoad.Name, "Agents.CrossRoadAgent", args);
                _crossRoadAgents.add(cont);
                cont.start();
            }

            for(SpawnPoint spawnPoint : _world.SpawnPoints)
            {
                addBehaviour(new SpawnCarBehavior(this, 10000, spawnPoint));
            }

            addBehaviour(new UpdateBehaviour(this, _updatePeriod));
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        _gui.setVisible(true);
    }

    private class SpawnCarBehavior extends TickerBehaviour
    {
        SpawnPoint _spawnPoint;

        public SpawnCarBehavior(Agent a, long t, SpawnPoint s)
        {
            super(a, t);
            _spawnPoint = s;
        }

        private List<Road> createPath(SpawnPoint start)
        {
            List<Road> path = new LinkedList<>();

            Road currentRoad = start.Road(), nextRoad;
            Place currentPlace = start, nextPlace = currentRoad.nextPlace(currentPlace);

            path.add(start.Road());
            while(!(nextPlace instanceof SpawnPoint)) {
                // Get all connections at the next place
                List<Road> connections = currentRoad.nextPlace(currentPlace).Connections;

                // Pick randomly one connection that's not the same as current road
                nextRoad = currentRoad;
                while (currentRoad == nextRoad)
                {
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
            WorldAgent agent = ((WorldAgent)getAgent());
            myLogger.log(Level.INFO, "Spawning car at: " + _spawnPoint.Name);

            ContainerController controller = getContainerController();
            Object[] args = {_spawnPoint, createPath(_spawnPoint)};

            try {
                AgentController cont = controller.createNewAgent("Car"+agent._carAgents.size(),
                        "Agents.CarAgent", args);
                agent._carAgents.add(cont);
                cont.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }

    private class UpdateBehaviour extends TickerBehaviour
    {

        public UpdateBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            //TODO: Update simulation

            //TODO: Update GUI
            //_gui.Update();
        }
    }
}
