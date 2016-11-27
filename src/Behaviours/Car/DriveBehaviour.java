package Behaviours.Car;

import Agents.CarAgent;
import Map.CrossRoad;
import Map.Place;
import Map.Road;
import Map.SpawnPoint;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;

import java.util.List;
import java.util.logging.Level;

/**
 * Created by adamj on 26.11.2016.
 */
public class DriveBehaviour extends OneShotBehaviour {
    private CarAgent _carAgent;

    public DriveBehaviour(CarAgent carAgent) {
        super(carAgent);
        _carAgent = carAgent;
    }

    @Override
    public void action() {

        int roadIndex = _carAgent.getCurrentRoadIndex();
        List<Road> path = _carAgent.getPath();

        Road currentRoad = path.get(roadIndex);
        Place destination = path.get(roadIndex).nextPlace(_carAgent.getOrigin());
        if (roadIndex == 1) {
            _carAgent.myLogger.log(Level.INFO, "Driving: CurrentIndex " + roadIndex + " origin: " + _carAgent.getOrigin().getClass().toString()
                    + " blabla " + path.get(roadIndex).getClass().toString());
        }

        // TODO simulates driving -> IMPROVE with encapsulation
        _carAgent.addBehaviour(new WakerBehaviour(_carAgent, Road.DRIVE_SPEED * currentRoad.Length) {
            @Override
            protected void onWake() {
                super.onWake();
                if (destination instanceof SpawnPoint) {
                    _carAgent.delete();
                } else if (destination instanceof CrossRoad) {
                    _carAgent.crossRoadArrived((CrossRoad) destination);
                }
            }
        });
    }
}