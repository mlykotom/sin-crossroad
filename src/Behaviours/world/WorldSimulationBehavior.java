package Behaviours.world;

import Agents.WorldAgent;
import Behaviours.state.AgentStatus;
import Map.CrossRoad;
import Map.SpawnPoint;
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


public class WorldSimulationBehavior extends TickerBehaviour {
    private static Logger sLogger = Logger.getMyLogger(WorldSimulationBehavior.class.getSimpleName());
    public static final String CONVERSATION_GET_AGENT_CURRENT_STATE = "conversation_get_car_status";
    public static final String CONVERSATION_GET_AGENT_CROSSROAD_TIME = "conversation_get_crossroad_time";
    private final WorldAgent mWorldAgent;
    private SearchConstraints mSearchConstraints = new SearchConstraints();
    private Date mSimulationStart;
    private AMSAgentDescription[] mAllFoundAgents;


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
        requestStaticStatus();
        mWorldAgent.updateWorld(ellapsedTime);
    }


    private void requestStaticStatus() {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setConversationId(CONVERSATION_GET_AGENT_CURRENT_STATE);

        try {
            AMSAgentDescription[] allFoundAgents = AMSService.search(mWorldAgent, new AMSAgentDescription(), mSearchConstraints);
            Arrays.stream(allFoundAgents)
                    .filter(amsAgentDescription -> {
                        String name = amsAgentDescription.getName().getLocalName();
                        return name.startsWith(CrossRoad.NAME_PREFIX) || name.startsWith(SpawnPoint.NAME_PREFIX);
                    })
                    .forEach(amsAgentDescription -> {
                        AID agentId = amsAgentDescription.getName();
                        request.addReceiver(agentId);
                    });

            mWorldAgent.send(request);
        } catch (FIPAException e) {
            e.printStackTrace();
        }
    }


    private void setupStatusReceiving() {
        mWorldAgent.addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchConversationId(CONVERSATION_GET_AGENT_CURRENT_STATE);
                ACLMessage msg = myAgent.receive(mt);
                if (msg == null) return;

                try {
                    AgentStatus status = (AgentStatus) msg.getContentObject();
                    mWorldAgent.setAgentStatus(status);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        });

        mWorldAgent.addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchConversationId(CONVERSATION_GET_AGENT_CROSSROAD_TIME);
                ACLMessage msg = myAgent.receive(mt);
                if (msg == null) return;

                try {
                    float time = (float) msg.getContentObject();
                    mWorldAgent.updateAverageWaitingTime(time);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private long calculateEllapsedTime() {
        return new Date().getTime() - mSimulationStart.getTime();
    }
}
