package Agents;

import Behaviours.Car.CrossBehaviour;
import Behaviours.Car.DriveBehaviour;
import Behaviours.state.AgentStatus;
import Behaviours.state.CarStatus;
import Behaviours.state.ReportStateBehaviour;
import Behaviours.state.StatefulAgent;
import Behaviours.world.WorldSimulationBehavior;
import Map.CrossRoad;
import Map.Place;
import Map.Road;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;


/**
 * Created by adamj on 22.11.2016.
 */
public class CarAgent extends StatefulAgent {
    public static final String CAR_NAME_PREFIX = "Car ";
    public Logger myLogger = Logger.getMyLogger(getClass().getName());

    private Place sourcePlace;
    private Place destinationPlace;
    private long timestampStart = 0;
    private long timestampEnd = 0;
    private List<Road> _path;
    private Place _origin;
    private int _currentRoadIdx = 0;


    public static String getAgentName(int id) {

        return String.format("%s%d", CAR_NAME_PREFIX, id);

    }


    @Override
    protected void setup() {
        Object args[] = getArguments();

        timestampStart = System.currentTimeMillis();

        _origin = (Place) args[0];
        _path = (List<Road>) args[1];
        myLogger.log(Level.INFO, getLocalName() + " driving");

        addBehaviour(new DriveBehaviour(this));
        addBehaviour(new ReportStateBehaviour(this));   // TODO put to base (didnt work o_O)
        //TODO: Model changed, car will receive full path
        //TODO: Use road.nextPlace with origin to navigate to next place
//        if (args.length < 2) {
//            System.err.println("Unexpected arguments for CarAgent. Call with <src> <dst>");
//            doDelete();
//        }
//
//
//        if(!(args[0] instanceof Place) || !(args[1] instanceof Place)) {
//            System.err.println("Malformed arguments for CarAgent.");
//            doDelete();
//        }
//
//        sourcePlace = (Place) args[0];
//        destinationPlace = (Place) args[1];
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
        myLogger.log(Level.INFO, getLocalName() + " arrived");
        ACLMessage msg = new ACLMessage(ACLMessage.PROPAGATE);
        msg.setConversationId(WorldSimulationBehavior.CONVERSATION_GET_AGENT_CURRENT_STATE);
        msg.addReceiver(WorldAgent.sWorldAgentAID);
        send(msg);
        doDelete();
    }


    public void placePassed(Place passedPlace) {
        _origin = passedPlace;
        _currentRoadIdx++;

        addBehaviour(new DriveBehaviour(this));
        myLogger.log(Level.INFO, getLocalName() + " crossRoad Passed!");
    }


    public void crossRoadArrived(CrossRoad crossRoad) {
        myLogger.log(Level.INFO, "CrossRoad arrived!");

        addBehaviour(new CrossBehaviour(this, crossRoad,
                _path.get(_currentRoadIdx), _path.get(_currentRoadIdx + 1)));
    }


    protected void takeDown() {
        timestampEnd = System.currentTimeMillis();
    }


    @Override
    public CarStatus getCurrentState() {
        return new Behaviours.state.CarStatus(
                getName(),
                _path,
                _currentRoadIdx,
                _origin,
                timestampStart, timestampEnd
        );
    }
}
