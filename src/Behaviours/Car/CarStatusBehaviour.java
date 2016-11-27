package Behaviours.Car;

import Agents.CarAgent;
import Common.CarStatus;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;

/**
 * Created by adamj on 27.11.2016.
 */
public class CarStatusBehaviour extends CyclicBehaviour {
    CarAgent _car;

    public CarStatusBehaviour(CarAgent agent)
    {
        super(agent);
        _car = agent;
    }

    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
        ACLMessage msg = myAgent.blockingReceive(mt);

        if(msg != null)
        {
            ACLMessage reply = msg.createReply();
            reply.setPerformative(ACLMessage.INFORM);

            CarStatus status = _car.getStatus();

            try {
                reply.setContentObject(status);
            } catch (IOException e) {
                e.printStackTrace();
            }

            myAgent.send(reply);
        }
    }
}
