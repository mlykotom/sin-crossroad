package Agents;


import Behaviours.CrossRoad.*;
import Behaviours.state.CrossRoadStatus;
import Behaviours.state.ReportStateBehaviour;
import Behaviours.state.StatefulAgent;
import Common.CarInCrossRoad;
import Common.DirectionType;
import Map.CrossRoadPlus;
import jade.core.AID;
import jade.util.Logger;
import model.Semaphore;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by raven on 23.11.2016.
 */
public class CrossRoadAgent extends StatefulAgent {
    public Logger myLogger = Logger.getMyLogger(getClass().getName());
    private CrossRoadPlus _crossRoad;


    @Override
    protected void setup() {
        super.setup();
        Object[] args = getArguments();
        _crossRoad = (CrossRoadPlus) args[0];
        addBehaviour(new ControlBehaviour(this, 20)); //TODO: Parametrize cycle duration?
        addBehaviour(new SemaphoreBehaviour(this));
        addBehaviour(new StartPassingBehaviour(this));
        addBehaviour(new EndPassingBehaviour(this));
        addBehaviour(new CrossRoadStateBehaviour(this));
        addBehaviour(new QueueBehaviour(this));

        CarsWaitingForSemaphoreChange = new LinkedList<>();
    }


    public List<AID> CarsWaitingForSemaphoreChange;

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

    public List<String> resolveExit(int exitId, DirectionType direction) {
        return _crossRoad.resolveExit(exitId, direction);
    }

    public List<CarInCrossRoad> getCarsIn() {
        return _crossRoad.CarsIn;
    }

    public Semaphore getSemaphore(int exitId, DirectionType direction) {
        return _crossRoad.resolveSemaphore(exitId, direction);
    }


    public void turnAllSemaphoresRed() {
        _crossRoad.turnAllSemaphoresRed();
    }


    @Override
    public CrossRoadStatus getCurrentState() {
        return new CrossRoadStatus(getName(), _crossRoad);
    }
}
