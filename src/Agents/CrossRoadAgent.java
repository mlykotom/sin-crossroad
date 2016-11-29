package Agents;


import Behaviours.CrossRoad.*;
import Common.CarInCrossRoad;
import Common.DirectionType;
import Map.CrossRoad;
import Map.Road;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by raven on 23.11.2016.
 */
public class CrossRoadAgent extends Agent {
    public Logger myLogger = Logger.getMyLogger(getClass().getName());
    private CrossRoad _crossRoad;
    private boolean _green;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        _crossRoad = (CrossRoad)args[0];
        addBehaviour(new ControlBehaviour(this, 6000));
        addBehaviour(new MessagingBehaviour(this));
        addBehaviour(new StartPassingBehaviour(this));
        addBehaviour(new EndPassingBehaviour(this));
        addBehaviour(new CrossRoadStateBehaviour(this));
        addBehaviour(new QueueBehaviour(this));

        _green = true;
        receivers = new LinkedList<>();
    }

    public List<AID> receivers;

    public boolean changeGreen()
    {
        _green = !_green;
        return _green;
    }

    public static final String CAR_JOINING_QUEUE = "car_joining_queue";
    public static final String CROSSROAD_QUEUE_RESPONSE = "crossroad_queue_response";
    public static final String FIRST_IN_QUEUE_RESPONSE = "first_in_queue_response";
    public static final String SEMAPHORE_CONVERSATION_REQUEST = "semaphore_conversation_request";
    public static final String SEMAPHORE_CONVERSATION_RESPONSE = "semaphore_conversation_response";
    public static final String SEMAPHORE_CHANGED = "semaphore_changed";

    public static final String CROSSROAD_STATE_REQUEST = "crossroad_state_request";
    public static final String CROSSROAD_STATE_RESPONSE = "crossroad_state_response";


    public static final String STATE_IN_CROSSROAD_CHANGED = "state_in_crossroad_changed";
    public static final String PASSING_CROSSROAD_INFORM = "passing_crossroad_inform";
    public static final String PASSED_CROSSROAD_INFORM = "passed_crossroad_inform";


    public List<String> CarsExitA = new LinkedList<>();
    public List<String> CarsExitB = new LinkedList<>();
    public List<String> CarsExitC = new LinkedList<>();
    public List<String> CarsExitD = new LinkedList<>();

    public List<String> CarsExitALeft = new LinkedList<>();
    public List<String> CarsExitBLeft = new LinkedList<>();
    public List<String> CarsExitCLeft = new LinkedList<>();
    public List<String> CarsExitDLeft = new LinkedList<>();

    public List<CarInCrossRoad> CarsIn = new LinkedList<>();

    public List<String> resolveExit(int exitId, DirectionType direction)
    {
        if(exitId == 0)
            return direction == DirectionType.Left ? CarsExitALeft : CarsExitA;
        else if(exitId == 1)
            return direction == DirectionType.Left ? CarsExitBLeft : CarsExitB;
        else if(exitId == 2)
            return direction == DirectionType.Left ? CarsExitCLeft : CarsExitC;
        else
            return direction == DirectionType.Left ? CarsExitDLeft : CarsExitD;
    }

    public boolean getGreen()
    {
        return _green;
    }



}
