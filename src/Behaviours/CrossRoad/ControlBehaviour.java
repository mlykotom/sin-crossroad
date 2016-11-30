package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Behaviour responsible for changing traffic lights.
 * It will periodically switch different traffic lights
 * on and off to let all cars through crossroad in optimal
 * time. Each configuration of traffic lights represents a
 * state. States are preselected and will repeat in cycle.
 * The cycle has specified time period.
 * The time allotted for each state will be adjusted
 * depending on how much cars are in each lane leading to
 * crossroad
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