package Behaviours.CrossRoad;

import Agents.CrossRoadAgent;
import Common.CarInCrossRoad;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;

/**
 * Created by adamj on 28.11.2016.
 */
public class EndPassingBehaviour extends CyclicBehaviour
{
    private final CrossRoadAgent _agent;

    public EndPassingBehaviour(CrossRoadAgent agent)
    {
        super(agent);
        _agent = agent;
    }
    @Override
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchConversationId(CrossRoadAgent.PASSED_CROSSROAD_INFORM);
        ACLMessage msg = myAgent.receive(mt);

        if(msg == null)
        {
            return;
        }

        Optional<CarInCrossRoad> exitingCar = _agent.CarsIn.stream()
                .filter((car) -> car.name == msg.getSender().getName())
                .findFirst();

        if(exitingCar.isPresent())
        {
            _agent.CarsIn.remove(exitingCar.get());

            // Inform other cars
            ACLMessage inform = new ACLMessage(ACLMessage.INFORM);
            inform.setConversationId(CrossRoadAgent.STATE_IN_CROSSROAD_CHANGED);

            List<String> carsToNotify = new LinkedList<>();
            addToListFirst(_agent.CarsExitALeft, carsToNotify);
            addToListFirst(_agent.CarsExitBLeft, carsToNotify);
            addToListFirst(_agent.CarsExitCLeft, carsToNotify);
            addToListFirst(_agent.CarsExitDLeft, carsToNotify);

            for (String receiver : carsToNotify) {
                inform.addReceiver(new AID(receiver));
            }
            myAgent.send(inform);
        }
    }

    public void addToListFirst(List<String> source, List<String> result)
    {
        if (!source.isEmpty())
            result.add(source.get(0));
    }
}
