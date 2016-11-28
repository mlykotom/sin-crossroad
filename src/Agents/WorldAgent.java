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

import java.util.*;
import java.util.logging.Level;


public class WorldAgent extends Agent {
    public static final long SPAWN_CAR_INTERVAL_MILLIS =  20 * DateUtils.MILLIS_PER_SECOND ;
    public static final long WORLD_UPDATE_PERIOD_MILLIS = 500;  // TODO parametrized
    private Logger myLogger = Logger.getMyLogger(getClass().getName());

    private BaseWorld mWorld;
    private List<AgentController> mCrossRoadAgents;
    private List<AgentController> mCarAgents;
    private MainGui mMainGui;
    private Date mSimulationStart;


    @Override
    protected void setup() {
        myLogger.log(Level.INFO, "Creating WorldOne");
        mSimulationStart = new Date();
        mWorld = new WorldOne();
        mMainGui = MainGui.runGUI(this);

        ContainerController controller = getContainerController();
        mCrossRoadAgents = new LinkedList<>();
        mCarAgents = new LinkedList<>();
        try {
            for (CrossRoad crossRoad : mWorld.CrossRoads) {
                Object[] args = {crossRoad};
                AgentController cont = controller.createNewAgent(crossRoad.getName(), CrossRoadAgent.class.getName(), args);
                mCrossRoadAgents.add(cont);
                cont.start();
            }

            for (SpawnPoint spawnPoint : mWorld.SpawnPoints) {
                addBehaviour(new SpawnCarBehavior(this, SPAWN_CAR_INTERVAL_MILLIS, spawnPoint));
            }

            addBehaviour(new WorldSimulationBehavior(this, WORLD_UPDATE_PERIOD_MILLIS));
        } catch (StaleProxyException e) {
            e.printStackTrace();
        }

        mMainGui.setVisible(true);
    }


    public MainGui getMainGui() {
        return mMainGui;
    }


    public Date getSimulationStart() {
        return mSimulationStart;
    }


    public List<AgentController> getCrossRoadAgents() {
        return mCrossRoadAgents;
    }


    public List<AgentController> getCarAgents() {
        return mCarAgents;
    }
}