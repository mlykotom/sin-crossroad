package Agents;

import Behaviours.state.AgentStatus;
import Behaviours.state.CarStatus;
import Behaviours.state.CrossRoadStatus;
import Behaviours.state.SpawnPointStatus;
import Behaviours.world.WorldSimulationBehavior;
import GUI.MainGui;
import jade.core.AID;
import jade.core.Agent;
import jade.util.Logger;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import model.*;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;


public class WorldAgent extends Agent {
    public static AID sWorldAgentAID;//TODO not immutable, not good, change!
    public static WorldAgent sInstance;
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
        sWorldAgentAID = getAID();
        sInstance = this;
        mWorld = new WorldOne();//WorldSimple()
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
            try {
                Object[] args = {spawnPoint};
                AgentController cont = mContainerController.createNewAgent(spawnPoint.getName(), SpawnPointAgent.class.getName(), args);
                cont.start();
                mSpawnPointsCount++;
            } catch (StaleProxyException e) {
                e.printStackTrace();
            }
        });
    }


    public void updateWorld(long ellapsedTime) {
        mMainGui.update(ellapsedTime);
    }


    public void setAgentStatus(AgentStatus agentStatus) {
        if (agentStatus instanceof CarStatus) {
            mMainGui.getWorldMap().setCarStatus((CarStatus) agentStatus);
        } else if (agentStatus instanceof CrossRoadStatus) {
            mMainGui.getWorldMap().setCrossRoadStatus((CrossRoadStatus) agentStatus);
        } else if (agentStatus instanceof SpawnPointStatus) {
            mMainGui.getWorldMap().setSpawnPointStatus((SpawnPointStatus) agentStatus);
        }
    }
}