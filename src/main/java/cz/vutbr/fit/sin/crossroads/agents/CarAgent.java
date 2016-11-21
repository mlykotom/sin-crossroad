package cz.vutbr.fit.sin.crossroads.agents;

import jade.core.Agent;

public class CarAgent extends Agent {
    @Override
    protected void setup() {
        super.setup();
        // Printout a welcome message
        System.out.println("Hello! Buyer-agent " + getAID().getName() + " is ready.");
    }
}
