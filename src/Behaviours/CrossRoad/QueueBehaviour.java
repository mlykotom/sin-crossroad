package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import Messages.CrossRoadArrivedMessage;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.List;

/**
 * Created by adamj on 28.11.2016.
 */
public class QueueBehaviour extends CyclicBehaviour
{
    private final CrossRoadAgent _agent;

    public QueueBehaviour(CrossRoadAgent agent)
    {
        super(agent);
        _agent = agent;
    }
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(CrossRoadAgent.CAR_JOINING_QUEUE);
        ACLMessage msg = myAgent.receive(mt);

        if(msg == null)
        {
            return;
        }

        CrossRoadArrivedMessage content;
        try {
            content = (CrossRoadArrivedMessage) msg.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
            return;
        }

        List<String> exitQueue = _agent.resolveExit(content.exitId, content.direction);
        exitQueue.add(msg.getSender().getName());

        ACLMessage reply = msg.createReply();
        reply.setConversationId(CrossRoadAgent.CROSSROAD_QUEUE_RESPONSE);
        if(exitQueue.get(0) == msg.getSender().getName())
        {
            reply.setPerformative(ACLMessage.AGREE);
        }
        else
        {
            reply.setPerformative(ACLMessage.REFUSE);
        }

        myAgent.send(reply);
    }
}