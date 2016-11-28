package Behaviours.Car;

import Agents.CarAgent;
import Behaviours.world.WorldSimulationBehavior;
import status.CarStatus;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;


/**
 * Created by adamj on 27.11.2016.
 */
public class CarStatusBehaviour extends CyclicBehaviour {
    CarAgent _car;

    public CarStatusBehaviour(CarAgent agent) {
        super(agent);
        _car = agent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(WorldSimulationBehavior.CONVERSATION_GET_CAR_STATUS);
        ACLMessage msg = myAgent.receive(mt);
        if (msg == null) return;

//        _car.myLogger.log(Level.INFO, msg.getContent());

        ACLMessage reply = msg.createReply();
        reply.setPerformative(ACLMessage.INFORM);
        reply.setConversationId(WorldSimulationBehavior.CONVERSATION_GET_CAR_STATUS);

        CarStatus status = _car.getStatus();

        try {
            reply.setContentObject(status);
        } catch (IOException e) {
            e.printStackTrace();
        }

        myAgent.send(reply);
    }
}
