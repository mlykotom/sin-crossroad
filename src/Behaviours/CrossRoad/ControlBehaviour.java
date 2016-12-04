package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import Common.DirectionType;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import model.Semaphore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * Behaviour responsible for changing traffic lights.
 * It will periodically switch different traffic lights
 * on and off to let all cars through crossroad in optimal
 * time. Each configuration of traffic lights represents a
 * state. States are preselected and will repeat in cycle.
 * The cycle has specified time period.
 * The time allotted for each state will be adjusted
 * depending on how much cars are in each lane leading to
 * crossroad
 */
public class ControlBehaviour extends TickerBehaviour
{
    private class Lane
    {
        public int ExitId;
        public DirectionType Direction;

        public Lane(int exitId, DirectionType direction)
        {
            ExitId = exitId;
            Direction = direction;
        }
    }

    private class State
    {
        public List<Lane> AllowedLanes; // Lanes allowed by this state
        public int WaitingCarsCount; // Number of cars waiting in lanes allowed by this state
        public float AllocatedTime; // Time allocated during cycle

        State()
        {
            AllowedLanes = new ArrayList<>();
        }
    }

    private final CrossRoadAgent _agent;
    private List<State> _states;
    private int _currentState;
    private float _cycleDuration;


    /**
     * @param agent Agent running this behaviour
     * @param cycleDuration Duration of cycle in seconds. One cycle is
     *                      iteration of all crossroad states once.
     */
    public ControlBehaviour(CrossRoadAgent agent, float cycleDuration) {
        super(agent, Integer.MAX_VALUE);
        _agent = agent;
        _cycleDuration = cycleDuration;

        // Configuration
        _states = new ArrayList<>();
        State state = new State();
        state.AllowedLanes.add(new Lane(0, DirectionType.Straight));
        state.AllowedLanes.add(new Lane(0, DirectionType.Left));
        state.AllowedLanes.add(new Lane(2, DirectionType.Straight));
        state.AllowedLanes.add(new Lane(2, DirectionType.Left));
        _states.add(state);
        state = new State();
        state.AllowedLanes.add(new Lane(0, DirectionType.Left));
        state.AllowedLanes.add(new Lane(2, DirectionType.Left));
        _states.add(state);
        state = new State();
        state.AllowedLanes.add(new Lane(1, DirectionType.Straight));
        state.AllowedLanes.add(new Lane(1, DirectionType.Left));
        state.AllowedLanes.add(new Lane(3, DirectionType.Straight));
        state.AllowedLanes.add(new Lane(3, DirectionType.Left));
        _states.add(state);
        state = new State();
        state.AllowedLanes.add(new Lane(1, DirectionType.Left));
        state.AllowedLanes.add(new Lane(3, DirectionType.Left));
        _states.add(state);

        _currentState = 0;
        updateTimeAllocations();
        recordCarCounts(_currentState);
        setState(_currentState);
    }

    private void updateTimeAllocations()
    {
        int totalCarCount = 0;
        for (State state : _states)
        {
            totalCarCount += state.WaitingCarsCount;
        }

        if (totalCarCount == 0)
        { // If there were no cars, allocate time evenly
            for (State s : _states)
                s.AllocatedTime = _cycleDuration / _states.size();
        }
        else {
            for (State state : _states) {
                state.AllocatedTime = ((float)state.WaitingCarsCount) / totalCarCount * _cycleDuration;
            }
        }
    }

    private void recordCarCounts(int stateIdx)
    {
        State state = _states.get(stateIdx);
        int totalCarCount = 0;
        for (Lane lane : state.AllowedLanes)
        {
            List<String> exit = _agent.resolveExit(lane.ExitId, lane.Direction);
            totalCarCount += exit.size();
        }
        state.WaitingCarsCount = totalCarCount;
    }

    private void setState(int stateIdx)
    {
        State state = _states.get(stateIdx);
        if ((int)(state.AllocatedTime * 1000) <= 0) {
            nextState();
            return;
        }
        // Change traffic lights according to state
        _agent.turnAllSemaphoresRed();
        for (Lane lane : state.AllowedLanes)
        {
            Semaphore sem = _agent.getSemaphore(lane.ExitId, lane.Direction);
            sem.setLight(Semaphore.Light.Green);
        }

        // Plan next change
        reset((int)(state.AllocatedTime * 1000));
    }

    private void nextState()
    {
        _currentState = (_currentState + 1) % _states.size();

        if (_currentState == 0)
            updateTimeAllocations();

        recordCarCounts(_currentState);

        setState(_currentState);
    }

    private void informWaitingCars()
    {
        if (_agent.CarsWaitingForSemaphoreChange.size() > 0) {
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.setConversationId(CrossRoadAgent.SEMAPHORE_CHANGED);

            for (AID receiver : _agent.CarsWaitingForSemaphoreChange) {
                msg.addReceiver(receiver);
            }
            _agent.CarsWaitingForSemaphoreChange.clear();
            myAgent.send(msg);
        }

        // Inform other cars
        ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
        inform.setConversationId(CrossRoadAgent.STATE_IN_CROSSROAD_CHANGED);

        List<String> carsToNotify = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            addToListFirst(_agent.resolveExit(i, DirectionType.Left), carsToNotify);
        }

        if (!carsToNotify.isEmpty()) {
            for (String receiver : carsToNotify) {
                inform.addReceiver(new AID(receiver, true));
            }
            myAgent.send(inform);
        }
    }

    private void addToListFirst(List<String> source, List<String> result) {
        if (!source.isEmpty())
            result.add(source.get(0));
    }

    @Override
    protected void onTick() {
        nextState();
        informWaitingCars();
    }
}