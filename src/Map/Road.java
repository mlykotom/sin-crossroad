package Map;

import model.Car;

import java.io.Serializable;
import java.util.LinkedList;


public class Road extends Place implements Serializable {
    public static final int DRIVE_SPEED = 100; // how long 1 length lasts   // TODO may be parametrized

    public final Place PlaceA;
    public final Place PlaceB;
    public final int Length; // in meters
    public LinkedList<Car> LaneToA;
    public LinkedList<Car> LaneToB;


    public Road(Place a, Place b) {
        super("Road " + a.getName() + " -> " + b.getName(), a.getCoordX(), a.getCoordY());
        PlaceA = a;
        PlaceB = b;
        Length = calculateLength(a, b);
    }


    public int calculateLength(Place a, Place b) {
        if (a.getCoordX() != b.getCoordX() && a.getCoordY() != b.getCoordY()) {
            throw new IllegalArgumentException("Road must be perpendicular to X or Y!");
        }

        return (int) Math.sqrt(Math.pow((a.getCoordX() - b.getCoordX()), 2) + Math.pow((a.getCoordY() - b.getCoordY()), 2));
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


    public int getBCoordX() {
        return PlaceB.getCoordX();
    }


    public int getBCoordY() {
        return PlaceB.getCoordY();
    }
}
