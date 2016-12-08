package Behaviours.Car;

import Agents.CarAgent;
import jade.util.Logger;
import Map.CrossRoad;
import Map.Place;
import Map.Road;
import Map.SpawnPoint;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.WakerBehaviour;
import org.apache.commons.lang3.time.DateUtils;

import java.util.List;


/**
 * Created by adamj on 26.11.2016.
 */
public class DriveBehaviour extends OneShotBehaviour {
    private static Logger sLogger = Logger.getMyLogger(DriveBehaviour.class.getSimpleName());

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

        _carAgent.reportRoad(true);

        double drivingTime = currentRoad.getLengthInMeters() / _carAgent.getSpeedInMeters(); // In seconds
        long wakeUpTime = (long) (drivingTime * DateUtils.MILLIS_PER_SECOND);
        //sLogger.log(Level.INFO, _carAgent.getLocalName() + " driving for " + drivingTime + "seconds");

        // TODO parameters
        _carAgent.addBehaviour(new WakerBehaviour(_carAgent, wakeUpTime) {
            @Override
            protected void onWake() {
                super.onWake();

                _carAgent.reportRoad(false);

                if (destination instanceof SpawnPoint) {
                    _carAgent.delete();
                } else if (destination instanceof CrossRoad) {
                    _carAgent.crossRoadArrived((CrossRoad) destination);
                } else {
                    _carAgent.placePassed(destination);
                }
            }
        });
    }
}