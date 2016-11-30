package Common;

/**
 * Created by raven on 25.11.2016.
 */
public class Car {
    public static double Speed = 50; // in km/h
    public static double Length = 3; // in meters


    public static double getSpeedInMeters()
    {
        return Speed / 3.6;
    }
}
