package Messages;

import Common.DirectionType;

import java.io.Serializable;

/**
 * Created by adamj on 28.11.2016.
 */
public class CrossRoadArrivedMessage implements Serializable {
    public int exitId;

    public DirectionType direction;

    public CrossRoadArrivedMessage(int exitId, DirectionType direction)
    {
        this.exitId = exitId;
        this.direction = direction;
    }
}


