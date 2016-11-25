package Agents;


import Map.CrossRoad;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

/**
 * Created by raven on 23.11.2016.
 */
public class CrossRoadAgent extends Agent {
    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    CrossRoad _crossRoad;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        _crossRoad = (CrossRoad)args[0];
        addBehaviour(new ControlBehavior());
    }

    private class ControlBehavior extends CyclicBehaviour
    {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if(msg != null){
                ACLMessage reply = msg.createReply();

                if(msg.getPerformative()== ACLMessage.QUERY_IF){
                    if (true){
                        reply.setPerformative(ACLMessage.AGREE);
                    }
                    else{
                        reply.setPerformative(ACLMessage.REFUSE);
                    }

                }
                else {
                    myLogger.log(Logger.INFO, "Agent "+getLocalName()+" - Unexpected message ["+ACLMessage.getPerformative(msg.getPerformative())+"] received from "+msg.getSender().getLocalName());
                    reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                    reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+") )");
                }
                send(reply);
            }
            else {
                block();
            }
        }
    }
}
