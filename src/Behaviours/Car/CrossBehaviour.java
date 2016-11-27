package Behaviours.Car;

import Agents.CarAgent;
import Map.CrossRoad;
import Map.Road;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

/**
 * Created by adamj on 26.11.2016.
 */
//TODO: The behaviour should ask if it is possible to cross with QUERY_IF performative.
//TODO: Crossroad will respond either ACCEPT or REFUSE.
//TODO: If refused, car will be informed when it is possible to cross with INFORM performative.
//TODO: If accepted, car should cross crossroad and move to next road by removing itself from FIFO of
//TODO: previous road and adding itself to FIFO of current road.
//TODO: Maybe it will need to be splitted into multiple behaviours because of message receiving
public class CrossBehaviour extends Behaviour
{
    private CrossRoad _crossRoad;
    private CarAgent _carAgent;
    private AID _crossRoadReceiver;
    private Road _roadFrom;
    private Road _roadTo;
    private boolean _crossed;

    public CrossBehaviour(CarAgent agent, CrossRoad crossRoad, Road from, Road to)
    {
        super(agent);
        _carAgent = agent;
        _crossRoad = crossRoad;
        _crossRoadReceiver = new AID(crossRoad.Name, AID.ISLOCALNAME);
        _roadFrom = from;
        _roadTo = to;
        _crossed = false;
    }

    @Override
    public void action() {
        ACLMessage msg = new ACLMessage(ACLMessage.QUERY_IF);
        msg.addReceiver(_crossRoadReceiver);
        _carAgent.send(msg);
        ACLMessage received = myAgent.blockingReceive();

        int carCanGo = received.getPerformative();
        if(carCanGo != ACLMessage.AGREE)
        {
            // Wait For Receiving message to go
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            myAgent.blockingReceive(mt);
        }

        // Start Behaviour for crossing next road
        _crossed = true;
        _carAgent.crossRoadPassed(_roadTo.currentPlace(_roadFrom));

        //TODO: Maybe use ReceiverBehaviour from JADE.
    }

    @Override
    public boolean done() {
        return _crossed;
    }
}
