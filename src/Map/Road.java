package Map;

import Common.Car;

import java.util.LinkedList;

/**
 * Created by raven on 23.11.2016.
 */
public class Road {
    public Place PlaceA;
    public Place PlaceB;
    public double Length; // in meters
    public LinkedList<Car> LaneToA;
    public LinkedList<Car> LaneToB;

    public Road(Place a, Place b, double length)
    {
        PlaceA = a;
        PlaceB = b;
        Length = length;
    }

    public Place nextPlace(Place currentPlace)
    {
        return currentPlace == PlaceA ? PlaceB : PlaceA;
    }

    public LinkedList<Car> GetLane(Place destination)
    {
        return destination == PlaceA ? LaneToA : LaneToB;
    }
}
