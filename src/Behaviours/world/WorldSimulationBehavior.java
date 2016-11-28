package Behaviours.world;

import Agents.CarAgent;
import Agents.WorldAgent;
import status.CarStatus;
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
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;


public class WorldSimulationBehavior extends TickerBehaviour {
    private static Logger sLogger = Logger.getMyLogger(WorldSimulationBehavior.class.getSimpleName());

    public static final String CONVERSATION_GET_CAR_STATUS = "conversation_get_car_status";

    private final WorldAgent mWorldAgent;
    private SearchConstraints mSearchConstraints = new SearchConstraints();

    private ConcurrentHashMap<String, CarStatus> mCarAgentStatus = new ConcurrentHashMap<>();


    public WorldSimulationBehavior(WorldAgent a, long period) {
        super(a, period);
        mWorldAgent = a;
        mWorldAgent.addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchConversationId(CONVERSATION_GET_CAR_STATUS);
                ACLMessage msg = myAgent.receive(mt);
                if (msg == null) return;

                try {
                    CarStatus status = (CarStatus) msg.getContentObject();
                    mCarAgentStatus.put(status.name, status);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }
        });
        mSearchConstraints.setMaxResults(-1L);
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
        return new Date().getTime() - mWorldAgent.getSimulationStart().getTime();
    }


    @Override
    protected void onTick() {
        long ellapsedTime = calculateEllapsedTime();
        //TODO: Update simulation

        requestCarStatus();
        mWorldAgent.getMainGui().update(ellapsedTime, mCarAgentStatus);
    }
}
