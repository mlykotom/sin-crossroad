package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

/**
 * Created by adamj on 26.11.2016.
 */
public class MessagingBehaviour extends CyclicBehaviour
{
    private final CrossRoadAgent _agent;

    public MessagingBehaviour(CrossRoadAgent agent)
    {
        super(agent);
        _agent = agent;
    }
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(CrossRoadAgent.SEMAPHORE_CONVERSATION_REQUEST);
        ACLMessage msg = myAgent.receive(mt);

        if(msg != null){
            ACLMessage reply = msg.createReply();
            reply.setConversationId(CrossRoadAgent.SEMAPHORE_CONVERSATION_RESPONSE);
            if(msg.getPerformative() == ACLMessage.QUERY_IF){
                if (_agent.getGreen()){
                        reply.setPerformative(ACLMessage.AGREE);
                    // Send only reply if agent can go
                }
                else{
                    _agent.receivers.add(msg.getSender());
                    reply.setPerformative(ACLMessage.REFUSE);
                }
            }
            else {
                _agent.myLogger.log(Logger.INFO, "Agent "+ myAgent.getLocalName()+" - Unexpected message ["+ACLMessage.getPerformative(msg.getPerformative())+"] received from "+msg.getSender().getLocalName());
                reply.setPerformative(ACLMessage.NOT_UNDERSTOOD);
                reply.setContent("( (Unexpected-act "+ACLMessage.getPerformative(msg.getPerformative())+") )");
            }
            myAgent.send(reply);
        }
        else {
            block();
        }
    }
}
