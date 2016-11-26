package Agents;


import Behaviours.CrossRoad.ControlBehaviour;
import Behaviours.CrossRoad.MessagingBehaviour;
import Map.CrossRoad;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.Logger;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by raven on 23.11.2016.
 */
public class CrossRoadAgent extends Agent {
    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    private CrossRoad _crossRoad;
    private boolean _green;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        _crossRoad = (CrossRoad)args[0];
        addBehaviour(new ControlBehaviour(this, 2300));
        addBehaviour(new MessagingBehaviour(this, this.myLogger));
        _green = true;
        receivers = new LinkedList<>();
    }

    public List<AID> receivers;

    public boolean changeGreen()
    {
        _green = !_green;
        return _green;
    }

    public boolean getGreen()
    {
        return _green;
    }



}
