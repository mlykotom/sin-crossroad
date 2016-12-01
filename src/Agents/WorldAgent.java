package Agents;

import Behaviours.state.AgentStatus;
import Behaviours.world.SpawnCarBehavior;
import Behaviours.world.WorldSimulationBehavior;
import GUI.MainGui;
import jade.core.Agent;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import model.BaseWorld;
import model.WorldSimple;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;


public class WorldAgent extends Agent {
    public static final long WORLD_UPDATE_PERIOD_MILLIS = 500;  // TODO parametrized
    private static Logger sLogger = Logger.getMyLogger(WorldAgent.class.getSimpleName());

    private BaseWorld mWorld;
    private int mCrossRoadsAgentsCount = 0;
    private int mCarAgentsCount = 0;
    private int mSpawnPointsCount = 0;
    private MainGui mMainGui;

    private ContainerController mContainerController;
    public final ConcurrentHashMap<String, AgentStatus> worldStatus = new ConcurrentHashMap<>();


    @Override
    protected void setup() {
        mWorld = new WorldSimple(); //WorldOne();
        mContainerController = getContainerController();
        sLogger.log(Level.INFO, "Creating " + mWorld.name);
        mMainGui = MainGui.runGUI(this);

        setupCrossRoads();
        setupSpawnPoints();
        setupWorldSimulation();

        mMainGui.setVisible(true);
    }


    private void setupWorldSimulation() {
        addBehaviour(new WorldSimulationBehavior(this, WORLD_UPDATE_PERIOD_MILLIS));
    }


    private void setupCrossRoads() {
        mWorld.CrossRoads.forEach((uuid, crossRoad) -> {
            try {
                Object[] args = {crossRoad};
                AgentController cont = mContainerController.createNewAgent(crossRoad.getName(), CrossRoadAgent.class.getName(), args);
                cont.start();
                mCrossRoadsAgentsCount++;
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        });
    }


    public BaseWorld getWorld() {
        return mWorld;
    }


    public synchronized int getCarAgentNewId() {
        return mCarAgentsCount++;
    }


    private void setupSpawnPoints() {
        mWorld.SpawnPoints.forEach((uuid, spawnPoint) -> {
            addBehaviour(new SpawnCarBehavior(this, spawnPoint));
            mSpawnPointsCount++;
        });
    }


    /**
     * @param ellapsedTime
     */
    public void updateWorld(long ellapsedTime) {
        mMainGui.update(ellapsedTime);
    }
}