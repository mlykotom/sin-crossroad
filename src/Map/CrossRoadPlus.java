package Map;

import Agents.CarAgent;
import Common.DirectionType;

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

    @Override
    public DirectionType resolveDirection(Road roadFrom, Road roadTo)
    {
        int roadFromIndex = resolveExitId(roadFrom);
        int roadToIndex = resolveExitId(roadTo);

        if ((roadFromIndex + 2) % 4 == roadToIndex)
            return DirectionType.Straight;
        else if ((roadFromIndex + 1) % 4 == roadToIndex)
            return DirectionType.Right;
        else
            return DirectionType.Left;
    }


    @Override
    public int resolveExitId(Road road)
    {
        return Connections.indexOf(road);
    }
}
