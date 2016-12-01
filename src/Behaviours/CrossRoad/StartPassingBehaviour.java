package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import Common.CarInCrossRoad;
import Map.CrossRoad;
import Map.Road;
import Map.SpawnPoint;
import Messages.CrossRoadArrivedMessage;
import Messages.CrossRoadPassingMessage;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by adamj on 28.11.2016.
 */
public class StartPassingBehaviour extends CyclicBehaviour
{
    private final CrossRoadAgent _agent;

    public StartPassingBehaviour(CrossRoadAgent agent)
    {
        super(agent);
        _agent = agent;
    }
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(CrossRoadAgent.PASSING_CROSSROAD_INFORM);
        ACLMessage msg = myAgent.receive(mt);

        if(msg == null)
        {
            return;
        }

        CrossRoadPassingMessage content;
        try {
            content = (CrossRoadPassingMessage) msg.getContentObject();
        } catch (UnreadableException e) {
            e.printStackTrace();
            return;
        }

        List<String> exitQueue = _agent.resolveExit(content.exitId, content.direction);
        // Add to current passing
        _agent.getCarsIn().add(new CarInCrossRoad(content.exitId, content.direction, msg.getSender().getName()));

        exitQueue.remove(msg.getSender().getName());

        if(!exitQueue.isEmpty())
        {
            // Wake up second car after first in queue
            // ToDo Change constant to some parameter
            _agent.addBehaviour(new WakerBehaviour(_agent, 500) {
                @Override
                protected void onWake() {
                    super.onWake();
                    if(!exitQueue.isEmpty())
                    {
                        String carAgent = exitQueue.get(0);
                        ACLMessage semaphoreMsg = new ACLMessage(ACLMessage.INFORM);
                        semaphoreMsg.setConversationId(CrossRoadAgent.FIRST_IN_QUEUE_RESPONSE);
                        semaphoreMsg.addReceiver(new AID(carAgent));
                        myAgent.send(semaphoreMsg);
                    }
                }
            });
        }
    }
}