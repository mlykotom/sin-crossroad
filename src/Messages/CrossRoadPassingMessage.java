package Messages;

import Common.DirectionType;

import java.io.Serializable;

/**
 * Created by adamj on 28.11.2016.
 */
public class CrossRoadPassingMessage implements Serializable {
    public int exitId;

    public DirectionType direction;

    public CrossRoadPassingMessage(int exitId, DirectionType direction)
    {
        this.exitId = exitId;
        this.direction = direction;
    }
}
