package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Created by adamj on 26.11.2016.
 */

public class ControlBehaviour extends TickerBehaviour
{
    private final CrossRoadAgent _agent;
    public ControlBehaviour(CrossRoadAgent agent, long period) {

        super(agent, period);
        _agent = agent;
    }

    @Override
    protected void onTick() {
        Boolean green = _agent.changeGreen();
        if (green) {
            if (_agent.receivers.size() > 0) {
                ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
                msg.setConversationId(CrossRoadAgent.SEMAPHORE_CHANGED);

                for (AID receiver : _agent.receivers) {
                    msg.addReceiver(receiver);
                }
                _agent.receivers.clear();
                myAgent.send(msg);
            }
        }
    }
}