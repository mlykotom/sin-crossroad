package Behaviours.Car;

import Agents.CarAgent;
import Agents.CrossRoadAgent;
import Common.DirectionType;
import Map.CrossRoad;
import Map.Road;
import Messages.CrossRoadArrivedMessage;
import Messages.CrossRoadPassingMessage;
import Messages.CrossRoadStateMessage;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.WakerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.io.IOException;
import java.util.logging.Level;

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
        // Joining queue
        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
        msg.setConversationId(CrossRoadAgent.CAR_JOINING_QUEUE);
        msg.addReceiver(_crossRoadReceiver);

        CrossRoadArrivedMessage infoMessage = new CrossRoadArrivedMessage(
                _crossRoad.resolveExitId(_roadFrom), _crossRoad.resolveDirection(_roadFrom, _roadTo)
        );
        try {
            msg.setContentObject(infoMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        myAgent.send(msg);

        MessageTemplate mt = MessageTemplate.MatchConversationId(CrossRoadAgent.CROSSROAD_QUEUE_RESPONSE);
        ACLMessage queueResponse = myAgent.blockingReceive(mt);

        if(queueResponse.getPerformative() != ACLMessage.AGREE) {
            _carAgent.myLogger.log(Level.WARNING, "wating in queueeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

            mt = MessageTemplate.MatchConversationId(CrossRoadAgent.FIRST_IN_QUEUE_RESPONSE);
            myAgent.blockingReceive(mt);
        }
        _carAgent.myLogger.log(Level.WARNING, "Iam HEREEEEEEEE");

        while(true){
            ACLMessage semaphoreMsg = new ACLMessage(ACLMessage.QUERY_IF);
            semaphoreMsg.setConversationId(CrossRoadAgent.SEMAPHORE_CONVERSATION_REQUEST);
            semaphoreMsg.addReceiver(_crossRoadReceiver);
            myAgent.send(semaphoreMsg);

            mt = MessageTemplate.MatchConversationId(CrossRoadAgent.SEMAPHORE_CONVERSATION_RESPONSE);
            ACLMessage received = myAgent.blockingReceive(mt);


            int carCanGo = received.getPerformative();
            if(carCanGo != ACLMessage.AGREE)
            {
                _carAgent.myLogger.log(Level.WARNING, "wating for GREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEN");

                // Wait For Receiving message to go
                mt = MessageTemplate.MatchConversationId(CrossRoadAgent.SEMAPHORE_CHANGED);
                myAgent.blockingReceive(mt);
                continue;
            }

            if(infoMessage.direction == DirectionType.Left)
            {
                ACLMessage stateMsg = new ACLMessage(ACLMessage.QUERY_IF);
                stateMsg.setConversationId(CrossRoadAgent.CROSSROAD_STATE_REQUEST);
                stateMsg.addReceiver(_crossRoadReceiver);
                try {
                    stateMsg.setContentObject(new CrossRoadStateMessage(
                            _crossRoad.resolveExitId(_roadFrom),_crossRoad.resolveDirection(_roadFrom, _roadTo)));
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                myAgent.send(stateMsg);

                mt = MessageTemplate.MatchConversationId(CrossRoadAgent.CROSSROAD_STATE_RESPONSE);
                received = myAgent.blockingReceive(mt);
                if(received.getPerformative() == ACLMessage.AGREE)
                {
                    _carAgent.myLogger.log(Level.WARNING, "LEAAAVING TURNING LEEEEEEEEEEEFT");
                    break;
                }

                _carAgent.myLogger.log(Level.WARNING, "Beeing blocked and trying to turn left. BLOOOOOOOOOOOOOOOOCKEEEEEEEEEEEEEEDDDDDDDDDDDDDDDDDD");

                mt = MessageTemplate.MatchConversationId(CrossRoadAgent.STATE_IN_CROSSROAD_CHANGED);
                myAgent.blockingReceive(mt);

                _carAgent.myLogger.log(Level.WARNING, "Igot unblocked: maybe");
            }
            else
            {
                _carAgent.myLogger.log(Level.WARNING, "Leaving this shitTTTTTTTTTTTTT");
                break;
            }
        }

        _carAgent.myLogger.log(Level.WARNING, "Yuhuuuu Im driving thourg crossroad");


        // Sending message that car is passing crossroad
        msg = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
        msg.setConversationId(CrossRoadAgent.PASSING_CROSSROAD_INFORM);
        msg.addReceiver(_crossRoadReceiver);

        CrossRoadPassingMessage passingMessage = new CrossRoadPassingMessage(
                _crossRoad.resolveExitId(_roadFrom), _crossRoad.resolveDirection(_roadFrom, _roadTo)
        );
        try {
            msg.setContentObject(passingMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        myAgent.send(msg);

        // TODO driving through crossroad time
        _carAgent.addBehaviour(new WakerBehaviour(_carAgent, 2000) {
            @Override
            protected void onWake() {
                super.onWake();

                // Sending End passing message
                ACLMessage msg = new ACLMessage(ACLMessage.REQUEST_WHENEVER);
                msg.setConversationId(CrossRoadAgent.PASSED_CROSSROAD_INFORM);
                msg.addReceiver(_crossRoadReceiver);
                myAgent.send(msg);

                _carAgent.myLogger.log(Level.WARNING, "I LOOOOOVE");
                // Start Behaviour for crossing next road
                _carAgent.placePassed(_crossRoad);
            }
        });

        _crossed = true;
        //TODO: Maybe use ReceiverBehaviour from JADE.
    }

    @Override
    public boolean done() {
        return _crossed;
    }
}
