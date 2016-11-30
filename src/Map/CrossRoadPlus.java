package Map;

import Agents.CarAgent;
import Common.DirectionType;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by raven on 23.11.2016.
 * Crossroad of following shape:
 * | D3 |
 * --     --
 * A0     C2
 * --     --
 * | B1 |
 */
public class CrossRoadPlus extends CrossRoad {

    public CrossRoadPlus(String name, int coordX, int coordY) {
        super(name, coordX, coordY);
    }


    public void setRoads(Road roadA, Road roadB, Road roadC, Road roadD) {
        addRoads(roadA, roadB, roadC, roadD);
    }

    public List<String> CarsExitA = new LinkedList<>();
    public List<String> CarsExitB = new LinkedList<>();
    public List<String> CarsExitC = new LinkedList<>();
    public List<String> CarsExitD = new LinkedList<>();


    public List<String> DrivingFromA = new LinkedList<>();
    public List<String> DrivingFromB = new LinkedList<>();
    public List<String> DrivingFromC = new LinkedList<>();
    public List<String> DrivingFromD = new LinkedList<>();

    @Override
    public boolean isExitEmpty(String carName, Road exit)
    {
        List<String> cars = resolveExit(exit);

        synchronized (cars) {
            boolean result = cars.isEmpty();

            if(result)
                return true;

            if(!cars.contains(carName))
            {
                cars.add(carName);
            }

            String firstCar = cars.get(0);
            if(firstCar == carName)
            {
                cars.remove(0);
                return true;
            }

            return false;
        }
    }

    @Override
    public boolean drive(String name, Road roadFrom, Road roadTo) {
        List<String> drivingCars = resolveDriving(roadFrom, roadTo);

        synchronized (drivingCars) {
            if(true)
            {
                drivingCars.add(name);
                return true;
            }

            return false;
        }
    }

    @Override
    public void passed(String name, Road roadFrom, Road roadTo) {
        List<String> drivingCars = resolveDriving(roadFrom, roadTo);

        synchronized (drivingCars) {
            drivingCars.remove(name);
        }
    }

    @Override
    public DirectionType resolveDirection(Road roadFrom, Road roadTo)
    {
        int roadFromIndex = resolveExitId(roadFrom);
        int roadToIndex = resolveExitId(roadTo);

        if ((roadFromIndex + 2) % 4 == roadToIndex)
            return DirectionType.Straight;
        else if ((roadFromIndex + 1) % 4 == roadToIndex)
            return DirectionType.Right;
        else
            return DirectionType.Left;
    }


    @Override
    public int resolveExitId(Road road)
    {
        return getRoads().indexOf(road);
    }

    public List<String> resolveExit(Road exit)
    {
        if(exit == getRoads().get(0))
            return CarsExitA;
        else if(exit == getRoads().get(1))
            return CarsExitB;
        else if(exit == getRoads().get(2))
            return CarsExitC;
        else
            return CarsExitD;
    }

    public List<String> resolveDriving(Road roadFrom, Road roadTo)
    {
        if(roadFrom == getRoads().get(0))
            return DrivingFromA;
        else if(roadFrom == getRoads().get(1))
            return DrivingFromB;
        else if(roadFrom == getRoads().get(2))
            return DrivingFromC;
        else
            return DrivingFromD;
    }
}
