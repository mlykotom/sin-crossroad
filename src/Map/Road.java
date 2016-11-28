package Map;

import model.Car;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by raven on 23.11.2016.
 */
public class Road implements Serializable {
    public static final int DRIVE_SPEED = 100; // how long 1 length lasts   // TODO may be parametrized

    public Place PlaceA;
    public Place PlaceB;
    public long Length; // in meters
    public LinkedList<Car> LaneToA;
    public LinkedList<Car> LaneToB;

    public Road(Place a, Place b, long length) {
        PlaceA = a;
        PlaceB = b;
        Length = length;
    }


    public Place nextPlace(Place currentPlace) {
        return currentPlace == PlaceA ? PlaceB : PlaceA;
    }

    public Place currentPlace(Road from) {
        return (from.PlaceA == PlaceA || from.PlaceB == PlaceA) ? PlaceA : PlaceB;
    }

    public LinkedList<Car> GetLane(Place destination) {
        return destination == PlaceA ? LaneToA : LaneToB;
    }
}
