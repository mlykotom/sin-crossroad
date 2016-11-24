package Agents;

import Map.Place;
import Map.Road;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
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

    @Override
    protected void setup() {
        Object args[] = getArguments();

        timestampStart = System.currentTimeMillis();

        Place origin = (Place)args[0];
        List<Road> path = (List<Road>)args[1];
        myLogger.log(Level.INFO, getLocalName()+" driving");
        addBehaviour(new CarDriveBehavior());

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

    private class CarDriveBehavior extends OneShotBehaviour
    {
        @Override
        public void action() {
            myLogger.log(Level.INFO, getLocalName() + " arrived");
            doDelete();
        }
    }
}
