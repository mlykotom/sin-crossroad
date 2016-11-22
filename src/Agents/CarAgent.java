package Agents;

import Common.Place;
import jade.core.Agent;

/**
 * Created by adamj on 22.11.2016.
 */
public class CarAgent extends Agent {

    private Place sourcePlace;
    private Place destinationPlace;
    private long timestampStart = 0;
    private long timestampEnd = 0;

    @Override
    protected void setup() {
        Object args[] = getArguments();

        timestampStart = System.currentTimeMillis();

        if (args.length < 2) {
            System.err.println("Unexpected arguments for CarAgent. Call with <src> <dst>");
            doDelete();
        }


        if(!(args[0] instanceof Place) || !(args[1] instanceof Place)) {
            System.err.println("Malformed arguments for CarAgent.");
            doDelete();
        }

        sourcePlace = (Place) args[0];
        destinationPlace = (Place) args[1];
    }

    protected void takeDown() {
        timestampEnd = System.currentTimeMillis();
        System.out.println("Car-agent " + getAID().getName() + "terminating.");
    }
}
