package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import Messages.CrossRoadArrivedMessage;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.util.Logger;
import model.Semaphore;


/**
 * Receives messages from Cars with request to pass crossroad.
 * Depending on the traffic light state, the crossroad will either
 * allow/agree crossing (green) or deny/refuse it (red).
 */
public class SemaphoreBehaviour extends CyclicBehaviour
{
    private final CrossRoadAgent _agent;

    public SemaphoreBehaviour(CrossRoadAgent agent)
    {
        super(agent);
        _agent = agent;
    }
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(CrossRoadAgent.SEMAPHORE_CONVERSATION_REQUEST);
        ACLMessage msg = myAgent.receive(mt);

        if(msg != null){
            CrossRoadArrivedMessage content;
            try {
                content = (CrossRoadArrivedMessage) msg.getContentObject();
            } catch (UnreadableException e) {
                e.printStackTrace();
                return;
            }

            ACLMessage reply = msg.createReply();
            reply.setConversationId(CrossRoadAgent.SEMAPHORE_CONVERSATION_RESPONSE);
            if(msg.getPerformative() == ACLMessage.QUERY_IF){
                Semaphore semaphore = _agent.getSemaphore(content.exitId, content.direction);
                if (semaphore.getLight() != Semaphore.Light.Red){
                    reply.setPerformative(ACLMessage.AGREE);
                }
                else{
                    _agent.CarsWaitingForSemaphoreChange.add(msg.getSender());
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
