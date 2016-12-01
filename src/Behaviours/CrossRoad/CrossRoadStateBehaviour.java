package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import Common.DirectionType;
import Messages.CrossRoadStateMessage;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by adamj on 29.11.2016.
 */

public class CrossRoadStateBehaviour extends CyclicBehaviour
{
    private final CrossRoadAgent _agent;

    public CrossRoadStateBehaviour(CrossRoadAgent agent)
    {
        super(agent);
        _agent = agent;
    }
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(CrossRoadAgent.CROSSROAD_STATE_REQUEST);
        ACLMessage msg = myAgent.receive(mt);

        if(msg == null)
        {
            return;
        }

        CrossRoadStateMessage content;
        try {
            content = (CrossRoadStateMessage) msg.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
            return;
        }

        // Getting opposite exit id
        int oppositeExitId = content.exitId + 2 % 4;

        ACLMessage reply = msg.createReply();
        reply.setConversationId(CrossRoadAgent.CROSSROAD_STATE_RESPONSE);
        reply.addReceiver(msg.getSender());

        if(_agent.getCarsIn().stream()
                .anyMatch((car) -> car.exitId == oppositeExitId && car.direction == DirectionType.Straight))
        {

            reply.setPerformative(ACLMessage.REFUSE);
        }
        else
        {
            reply.setPerformative(ACLMessage.AGREE);
        }

        myAgent.send(reply);
    }
}