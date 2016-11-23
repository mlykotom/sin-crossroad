package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

/**
 * Created by raven on 22.11.2016.
 */
public class TestAgent extends Agent {
    private Logger myLogger = Logger.getMyLogger(getClass().getName());

    private AID _pingReceiver;

    protected void setup()
    {
        System.out.println("Holaaa");
        PingBehaviour PingBehaviour = new PingBehaviour(this, 1000);
        addBehaviour(PingBehaviour);
        _pingReceiver = new AID("ping", AID.ISLOCALNAME);
    }

    private class PingBehaviour extends TickerBehaviour
    {
        PingBehaviour(Agent a, int t) {super(a, t);}

        @Override
        protected void onTick() {
            myLogger.log(Logger.INFO, "Ping");
            ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
            msg.setContent("ping");
            msg.addReceiver(_pingReceiver);
            send(msg);
        }
    }
}
