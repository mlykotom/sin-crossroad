package Common;

import jade.core.AID;

/**
 * Created by adamj on 28.11.2016.
 */
public class CarInCrossRoad {
    public int exitId;
    public DirectionType direction;
    public String name;

    public CarInCrossRoad(int exitId, DirectionType direction, String name)
    {
        this.exitId = exitId;
        this.direction = direction;
        this.name = name;
    }
}
