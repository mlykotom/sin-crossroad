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
        Place destination = currentRoad.nextPlace(_carAgent.getOrigin());

        // TODO simulates driving -> IMPROVE with encapsulation
        // TODO parameters
        _carAgent.addBehaviour(new WakerBehaviour(_carAgent, 2000) {
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