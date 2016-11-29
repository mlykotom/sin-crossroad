package Messages;

import Common.DirectionType;

import java.io.Serializable;

/**
 * Created by adamj on 29.11.2016.
 */
public class CrossRoadStateMessage implements Serializable {
    public int exitId;

    public DirectionType direction;

    public CrossRoadStateMessage(int exitId, DirectionType direction)
    {
        this.exitId = exitId;
        this.direction = direction;
    }
}