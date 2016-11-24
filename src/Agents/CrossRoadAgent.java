package Agents;


import Map.CrossRoad;
import jade.core.Agent;
import jade.util.Logger;

/**
 * Created by raven on 23.11.2016.
 */
public class CrossRoadAgent extends Agent {
    private Logger myLogger = Logger.getMyLogger(getClass().getName());
    CrossRoad _crossRoad;

    @Override
    protected void setup() {
        Object[] args = getArguments();
        _crossRoad = (CrossRoad)args[0];
    }
}
