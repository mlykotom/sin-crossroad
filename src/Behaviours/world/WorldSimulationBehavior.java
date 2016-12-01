package Behaviours.world;

import Agents.CarAgent;
import Agents.WorldAgent;
import Behaviours.state.AgentStatus;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import jade.util.Logger;

import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;


public class WorldSimulationBehavior extends TickerBehaviour {
    private static Logger sLogger = Logger.getMyLogger(WorldSimulationBehavior.class.getSimpleName());
    public static final String CONVERSATION_GET_AGENT_CURRENT_STATE = "conversation_get_car_status";
    private final WorldAgent mWorldAgent;
    private SearchConstraints mSearchConstraints = new SearchConstraints();
    private Date mSimulationStart;


    public WorldSimulationBehavior(WorldAgent a, long period) {
        super(a, period);
        mWorldAgent = a;
        mSearchConstraints.setMaxResults(-1L);
        setupStatusReceiving();
        mSimulationStart = new Date();
    }


    /**
     * Updates simulation (retrieves world's state)
     */
    @Override
    protected void onTick() {
        long ellapsedTime = calculateEllapsedTime();
        requestCarStatus();
        mWorldAgent.updateWorld(ellapsedTime);
    }


    private void setupStatusReceiving() {
        mWorldAgent.addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchConversationId(CONVERSATION_GET_AGENT_CURRENT_STATE);
                ACLMessage msg = myAgent.receive(mt);
                if (msg == null) return;

                if (msg.getPerformative() == ACLMessage.PROPAGATE) {
                    mWorldAgent.worldStatus.remove(msg.getSender().getName());
                    sLogger.log(Level.INFO, "deleting agent " + msg.getSender().getLocalName());
                    return;
                }

                try {
                    AgentStatus status = (AgentStatus) msg.getContentObject();
                    mWorldAgent.worldStatus.put(status.getAgentId(), status);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void requestCarStatus() {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setConversationId(CONVERSATION_GET_AGENT_CURRENT_STATE);

        try {
            Arrays.stream(AMSService.search(mWorldAgent, new AMSAgentDescription(), mSearchConstraints))
                    .filter(amsAgentDescription -> amsAgentDescription.getName().getLocalName().startsWith(CarAgent.CAR_NAME_PREFIX))
                    .forEach(amsAgentDescription -> {
                        AID agentId = amsAgentDescription.getName();
                        request.addReceiver(agentId);
                    });

            mWorldAgent.send(request);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }


    private long calculateEllapsedTime() {
        return new Date().getTime() - mSimulationStart.getTime();
    }
}
