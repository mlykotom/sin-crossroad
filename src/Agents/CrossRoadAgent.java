package Agents;


import Map.CrossRoad;
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
    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    private CrossRoad _crossRoad;
    private List<AID> _receivers;
    private boolean _green;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        _crossRoad = (CrossRoad)args[0];
        addBehaviour(new ControlBehaviour(this, 2300));
        addBehaviour(new MessagingBehaviour());
        _green = true;
        _receivers = new LinkedList<>();
    }

    private class ControlBehaviour extends TickerBehaviour
    {
        public ControlBehaviour(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            _green = !_green;
            if (_green) {
                if (_receivers.size() > 0) {
                    ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                    for (AID receiver : _receivers) {
                        msg.addReceiver(receiver);
                    }
                    _receivers.clear();
                    send(msg);
                }
            }
        }
    }

    private class MessagingBehaviour extends CyclicBehaviour
    {
        @Override
        public void action() {
            ACLMessage msg = myAgent.receive();
            if(msg != null){
                ACLMessage reply = msg.createReply();

                if(msg.getPerformative() == ACLMessage.QUERY_IF){
                    if (_green){
                        reply.setPerformative(ACLMessage.AGREE);
                    }
                    else{
                        _receivers.add(msg.getSender());
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
