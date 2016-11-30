package Map;

import Common.Car;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by raven on 23.11.2016.
 */
public class Road implements Serializable {
    public Place PlaceA;
    public Place PlaceB;
    public long Length; // in meters

    public Road(Place a, Place b, long length) {
        PlaceA = a;
        PlaceB = b;
        Length = length;
    }

    public Place nextPlace(Place currentPlace) {
        return currentPlace == PlaceA ? PlaceB : PlaceA;
    }
}
