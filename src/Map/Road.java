package Map;

import java.io.Serializable;


public class Road extends Place implements Serializable {
    public static final int METERS_PER_POINT = 20;

    protected final Place mPlaceA;
    protected final Place mPlaceB;
    protected final int mLengthInPoints; // in meters


    public Road(Place a, Place b) {
        super("Road " + a.getName() + " -> " + b.getName(), a.getCoordX(), a.getCoordY());
        mPlaceA = a;
        mPlaceB = b;
        mLengthInPoints = calculateLengthInPoints(a, b);
    }


    private int calculateLengthInPoints(Place a, Place b) {
        if (a.getCoordX() != b.getCoordX() && a.getCoordY() != b.getCoordY()) {
            throw new IllegalArgumentException("Road must be perpendicular to X or Y!");
        }

        return (int) Math.sqrt(Math.pow((a.getCoordX() - b.getCoordX()), 2) + Math.pow((a.getCoordY() - b.getCoordY()), 2));
    }


    public int getLengthInMeters() {
        return mLengthInPoints * METERS_PER_POINT;
    }


    public int getLengthInPoints() {
        return mLengthInPoints;
    }


    public Place nextPlace(Place currentPlace) {
        return currentPlace == mPlaceA ? mPlaceB : mPlaceA;
    }


    public int getBCoordX() {
        return mPlaceB.getCoordX();
    }


    public int getBCoordY() {
        return mPlaceB.getCoordY();
    }


    public Place getPlaceA() {
        return mPlaceA;
    }


    public Place getPlaceB() {
        return mPlaceB;
    }
}
