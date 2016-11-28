package Map;

import Agents.CarAgent;
import Common.DirectionType;

import java.util.List;
public abstract class CrossRoad extends Place {
    public CrossRoad(String name, int coordX, int coordY) {
        super(name, coordX, coordY);
    }

    public abstract boolean isExitEmpty(String CarName, Road exit);

    public abstract boolean drive(String name, Road roadFrom, Road roadTo);

    public abstract void passed(String name, Road roadFrom, Road roadTo);

    public abstract DirectionType resolveDirection(Road roadFrom, Road roadTo);

    public abstract int resolveExitId(Road road);

}
