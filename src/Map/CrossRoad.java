package Map;

import Agents.CarAgent;
import Common.DirectionType;

import java.util.List;

/**
 * Created by raven on 23.11.2016.
 */
public abstract class CrossRoad extends Place {
    public String Name;

    CrossRoad()
    {
        super();
        Name = "";
    }

    CrossRoad(String name)
    {
        super();
        Name = name;
    }

    public abstract DirectionType resolveDirection(Road roadFrom, Road roadTo);

    public abstract int resolveExitId(Road road);

}
