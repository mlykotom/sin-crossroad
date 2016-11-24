package Map;

/**
 * Created by raven on 23.11.2016.
 */
public class Road {
    public Place PlaceA;
    public Place PlaceB;

    public Road(Place a, Place b)
    {
        PlaceA = a;
        PlaceB = b;
    }

    public Place nextPlace(Place currentPlace)
    {
        return currentPlace == PlaceA ? PlaceB : PlaceA;
    }
}
