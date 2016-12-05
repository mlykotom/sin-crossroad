package Agents;

import Behaviours.Car.CrossBehaviour;
import Behaviours.Car.DriveBehaviour;
import Behaviours.state.CarStatus;
import Behaviours.world.WorldSimulationBehavior;
import Map.CrossRoad;
import Map.Place;
import Map.Road;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;


/**
 * Created by adamj on 22.11.2016.
 */
public class CarAgent extends Agent {
    public static final String CAR_NAME_PREFIX = "Car::";
    public Logger myLogger = Logger.getMyLogger(getClass().getName());

    private long timestampStart = 0;
    private long timestampEnd = 0;
    private List<Road> _path;
    private Place _origin;
    private int _currentRoadIdx = 0;


    public static String getAgentName(int id) {
        return String.format("%s%d", CAR_NAME_PREFIX, id);
    }


    @SuppressWarnings("unchecked")
    @Override
    protected void setup() {
        super.setup();
        Object args[] = getArguments();

        timestampStart = System.currentTimeMillis();
        _origin = (Place) args[0];
        _path = (List<Road>) args[1];

        addBehaviour(new DriveBehaviour(this));
    }


    public int getCurrentRoadIndex() {
        return _currentRoadIdx;
    }


    public List<Road> getPath() {
        return _path;
    }


    public Place getOrigin() {
        return _origin;
    }


    public void delete() {
        //myLogger.log(Level.INFO, getLocalName() + " arrived");
//        reportRoad(false);
        doDelete();
    }


    /**
     * @deprecated
     */
    private void sendDeleteMsg() {
        // send ending status to world about end
        ACLMessage msg = new ACLMessage(ACLMessage.PROPAGATE);
        msg.setConversationId(WorldSimulationBehavior.CONVERSATION_GET_AGENT_CURRENT_STATE);
        msg.addReceiver(WorldAgent.sWorldAgentAID);
        send(msg);
    }


    public void placePassed(Place passedPlace) {
        _origin = passedPlace;
        _currentRoadIdx++;

        addBehaviour(new DriveBehaviour(this));
        //myLogger.log(Level.INFO, getLocalName() + " crossRoad Passed!");
    }


    public void crossRoadArrived(CrossRoad crossRoad) {
        //myLogger.log(Level.INFO, "CrossRoad arrived!");

        addBehaviour(new CrossBehaviour(this, crossRoad,
                _path.get(_currentRoadIdx), _path.get(_currentRoadIdx + 1)));
    }


    protected void takeDown() {
        timestampEnd = System.currentTimeMillis();
    }


    public void reportRoad(boolean enteredRoad) {
        try {
            CarStatus carStatus = new CarStatus(getName(), _path, _currentRoadIdx, _origin, enteredRoad, timestampStart, timestampEnd);

            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setConversationId(WorldSimulationBehavior.CONVERSATION_GET_AGENT_CURRENT_STATE);
            msg.addReceiver(WorldAgent.sWorldAgentAID);
            msg.setContentObject(carStatus);
            send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportCrossRoadWaitingTime(float time)
    {
        try {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setConversationId(WorldSimulationBehavior.CONVERSATION_GET_AGENT_CROSSROAD_TIME);
            msg.addReceiver(WorldAgent.sWorldAgentAID);
            msg.setContentObject(time);
            send(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
