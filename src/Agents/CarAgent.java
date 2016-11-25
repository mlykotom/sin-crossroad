package Agents;

import Map.CrossRoad;
import Map.Place;
import Map.Road;
import Map.SpawnPoint;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by adamj on 22.11.2016.
 */
public class CarAgent extends Agent {
    private Logger myLogger = Logger.getMyLogger(getClass().getName());

    private Place sourcePlace;
    private Place destinationPlace;
    private long timestampStart = 0;
    private long timestampEnd = 0;
    private List<Road> _path;
    private Place _origin;
    private int _currentRoadIdx;

    @Override
    protected void setup() {
        Object args[] = getArguments();

        timestampStart = System.currentTimeMillis();

        _origin = (Place)args[0];
        _path = (List<Road>)args[1];
        _currentRoadIdx = 0;
        myLogger.log(Level.INFO, getLocalName()+" driving");
        addBehaviour(new DriveBehaviour());

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

    protected void takeDown() {
        timestampEnd = System.currentTimeMillis();
    }

    private class DriveBehaviour extends OneShotBehaviour
    {
        @Override
        public void action() {
            Place destination = _path.get(_currentRoadIdx).nextPlace(_origin);
            if (destination instanceof SpawnPoint)
            {
                myLogger.log(Level.INFO, getLocalName() + " arrived");
                doDelete();
            }
            else if (destination instanceof CrossRoad)
            {
                addBehaviour(new CrossBehaviour(myAgent, (CrossRoad)destination,
                        _path.get(_currentRoadIdx), _path.get(_currentRoadIdx+1)));
            }
        }
    }

    private class CanCrossBehaviour extends Behaviour
    {

        @Override
        public void action() {

        }

        @Override
        public boolean done() {
            return false;
        }
    }

    //TODO: The behaviour should ask if it is possible to cross with QUERY_IF performative.
    //TODO: Crossroad will respond either ACCEPT or REFUSE.
    //TODO: If refused, car will be informed when it is possible to cross with INFORM performative.
    //TODO: If accepted, car should cross crossroad and move to next road by removing itself from FIFO of
    //TODO: previous road and adding itself to FIFO of current road.
    //TODO: Maybe it will need to be splitted into multiple behaviours because of message receiving
    private class CrossBehaviour extends Behaviour
    {
        private CrossRoad _crossRoad;
        private AID _crossRoadReceiver;
        private Road _roadFrom;
        private Road _roadTo;
        private boolean _crossed;

        CrossBehaviour(Agent a, CrossRoad crossRoad, Road from, Road to)
        {
            super(a);
            _crossRoad = crossRoad;
            _crossRoadReceiver = new AID(crossRoad.Name, AID.ISLOCALNAME);
            _roadFrom = from;
            _roadTo = to;
            _crossed = false;
        }

        @Override
        public void action() {
            ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
            msg.addReceiver(_crossRoadReceiver);
            send(msg);
            myAgent.receive(); //TODO: This will receive any message, but should be only ACCEPT or REFUSE
            //TODO: Maybe use ReceiverBehaviour from JADE.
        }

        @Override
        public boolean done() {
            return _crossed;
        }
    }
}
