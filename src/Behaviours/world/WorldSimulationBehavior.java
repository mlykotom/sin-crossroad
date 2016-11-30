package Behaviours.world;

import Agents.CarAgent;
import Agents.WorldAgent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.MessageTemplate;
import jade.lang.acl.UnreadableException;
import status.CarStatus;
import jade.core.AID;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


public class WorldSimulationBehavior extends TickerBehaviour {
    public static final String CONVERSATION_GET_CAR_STATUS = "conversation_get_car_status";
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
                MessageTemplate mt = MessageTemplate.MatchConversationId(CONVERSATION_GET_CAR_STATUS);
                ACLMessage msg = myAgent.receive(mt);
                if (msg == null) return;

                try {
                    CarStatus status = (CarStatus) msg.getContentObject();
//                    mCarAgentStatus.put(status.name, status);
                    mWorldAgent.updateStatus(status);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void requestCarStatus() {
        ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
        request.setConversationId(CONVERSATION_GET_CAR_STATUS);

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
