package Map;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by raven on 23.11.2016.
 * Crossroad of following shape:
 *   | D3 |
 * --     --
 * A0     C2
 * --     --
 *   | B1 |
 *
 *         | [0,-1] |
 * --------          --------
 * [-1,-1]   [0, 0]   [1, 0]
 * --------          --------
 *         | [0, 1] |
 *
 */
public class CrossRoadPlus extends CrossRoad {
    public CrossRoadPlus()
    {
        super();
    }

    public CrossRoadPlus(String name)
    {
        super(name);
    }

    public void SetExits(Road exitA, Road exitB, Road exitC, Road exitD)
    {
        Connections.add(exitA);
        Connections.add(exitB);
        Connections.add(exitC);
        Connections.add(exitD);
    }
}
