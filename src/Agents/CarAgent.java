package Agents;

import Behaviours.Car.CrossBehaviour;
import Behaviours.Car.DriveBehaviour;
import Common.CarStatus;
import Map.CrossRoad;
import Map.Place;
import Map.Road;
import jade.core.Agent;
import jade.util.Logger;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by adamj on 22.11.2016.
 */
public class CarAgent extends Agent {
    public Logger myLogger = Logger.getMyLogger(getClass().getName());

    private Place sourcePlace;
    private Place destinationPlace;
    private long timestampStart = 0;
    private long timestampEnd = 0;
    private List<Road> _path;
    private Place _origin;
    private int _currentRoadIdx;

    @Override
    protected void setup() {
        Object args[] = getArguments();

        timestampStart = System.currentTimeMillis();

        _origin = (Place)args[0];
        _path = (List<Road>)args[1];
        _currentRoadIdx = 0;
        myLogger.log(Level.INFO, getLocalName()+" driving");
        addBehaviour(new DriveBehaviour(this));

        //TODO: Model changed, car will receive full path
        //TODO: Use road.nextPlace with origin to navigate to next place
//        if (args.length < 2) {
//            System.err.println("Unexpected arguments for CarAgent. Call with <src> <dst>");
//            doDelete();
//        }
//
//
//        if(!(args[0] instanceof Place) || !(args[1] instanceof Place)) {
//            System.err.println("Malformed arguments for CarAgent.");
//            doDelete();
//        }
//
//        sourcePlace = (Place) args[0];
//        destinationPlace = (Place) args[1];
    }

    public int getCurrentRoadIndex() { return _currentRoadIdx; }

    public List<Road> getPath()
    {
        return _path;
    }

    public Place getOrigin()
    {
        return _origin;
    }

    public void delete()
    {
        myLogger.log(Level.INFO, getLocalName() + " arrived");
        doDelete();
    }

    public void crossRoadPassed(Place destination)
    {
        _origin = destination;
        _currentRoadIdx++;
        addBehaviour(new DriveBehaviour(this));
        myLogger.log(Level.INFO, "CrossRoad Passed!");
    }

    public void crossRoadArrived(CrossRoad crossRoad)
    {
        addBehaviour(new CrossBehaviour(this, crossRoad,
                _path.get(_currentRoadIdx), _path.get(_currentRoadIdx+1)));
    }

    protected void takeDown() {
        timestampEnd = System.currentTimeMillis();
    }

    public CarStatus getStatus() {
        return new CarStatus(getName(), sourcePlace, destinationPlace,
                timestampStart, timestampEnd, _path.get(_currentRoadIdx));
    }
}
