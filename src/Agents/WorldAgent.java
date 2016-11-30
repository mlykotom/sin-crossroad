package Agents;

import Behaviours.world.SpawnCarBehavior;
import Behaviours.world.WorldSimulationBehavior;
import GUI.MainGui;
import Map.*;
import jade.core.Agent;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import model.BaseWorld;
import model.WorldOne;
import org.apache.commons.lang3.time.DateUtils;
import status.CarStatus;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;


public class WorldAgent extends Agent {
    public static final long SPAWN_CAR_INTERVAL_MILLIS =  20 * DateUtils.MILLIS_PER_SECOND ;
    public static final long WORLD_UPDATE_PERIOD_MILLIS = 500;  // TODO parametrized
    private static Logger sLogger = Logger.getMyLogger(WorldAgent.class.getSimpleName());

    private BaseWorld mWorld;
    private List<AgentController> mCarAgents;
    private int mCrossRoadsAgentsCount = 0;
    private int mCarAgentsCount = 0;
    private int mSpawnPointsCount = 0;
    private MainGui mMainGui;

    private ContainerController mContainerController;


    @Override
    protected void setup() {
        mWorld = new WorldOne();
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
        for (CrossRoad crossRoad : mWorld.CrossRoads) {
            try {
                Object[] args = {crossRoad};
                AgentController cont = mContainerController.createNewAgent(crossRoad.getName(), CrossRoadAgent.class.getName(), args);
                mCrossRoadsAgentsCount++;
                cont.start();
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        }
    }


    public BaseWorld getWorld() {
        return mWorld;
    }


    public synchronized int getCarAgentNewId() {
        return mCarAgentsCount++;
    }


    private void setupSpawnPoints() {
        mWorld.SpawnPoints.forEach(spawnPoint -> {
            addBehaviour(new SpawnCarBehavior(this, SPAWN_CAR_INTERVAL_MILLIS, spawnPoint));
            mSpawnPointsCount++;
        });
    }


    /**
     * @param ellapsedTime
     */
    public void updateWorld(long ellapsedTime) {
        mMainGui.update(ellapsedTime);
    }


    /**
     * Extend for any status
     * @param status
     */
    public void updateStatus(CarStatus status) {
        mMainGui.updateStatus(status);
    }
}