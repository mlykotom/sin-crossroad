package Behaviours.state;

import Behaviours.world.WorldSimulationBehavior;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.util.Logger;

import java.io.IOException;
import java.util.logging.Level;


public class ReportStateBehaviour extends CyclicBehaviour {
    private static final Logger sLogger = Logger.getMyLogger(ReportStateBehaviour.class.getSimpleName());
    private final StatefulAgent mStatefulAgent;


    public ReportStateBehaviour(StatefulAgent agent) {
        super(agent);
        mStatefulAgent = agent;
    }


    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(WorldSimulationBehavior.CONVERSATION_GET_AGENT_CURRENT_STATE);
        ACLMessage msg = myAgent.receive(mt);
        if (msg == null) return;

        try {
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);
            reply.setConversationId(WorldSimulationBehavior.CONVERSATION_GET_AGENT_CURRENT_STATE);
            reply.setContentObject(mStatefulAgent.getCurrentState());
            myAgent.send(reply);
        } catch (IOException e) {
            e.printStackTrace();
            sLogger.log(Level.WARNING, "Could not send reply to world Behaviours.state request!");
        }
    }


    /**
     * TODO solve it
     */
    public static class EndReportStateBehaviour extends OneShotBehaviour {
        private final StatefulAgent mStatefulAgent;


        public EndReportStateBehaviour(StatefulAgent agent) {
            super(agent);
            mStatefulAgent = agent;
        }


        @Override
        public void action() {
            try {
                ACLMessage reply = new ACLMessage(ACLMessage.INFORM);
                reply.setConversationId(WorldSimulationBehavior.CONVERSATION_GET_AGENT_CURRENT_STATE);
                reply.setContentObject(mStatefulAgent.getCurrentState());
                myAgent.send(reply);
            } catch (IOException e) {
                e.printStackTrace();
                sLogger.log(Level.WARNING, "Could not send reply to world Behaviours.state request!");
            }
        }
    }
}
