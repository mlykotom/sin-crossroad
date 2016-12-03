package Map;

import Common.DirectionType;
import model.Semaphore;

import java.util.ArrayList;
import java.util.List;


public abstract class CrossRoad extends Place {
    public static final String NAME_PREFIX = "Crossroad::";

    protected List<Semaphore> mSemaphores = new ArrayList<>();


    public CrossRoad(String name, int coordX, int coordY) {
        super(name, coordX, coordY);
    }


    public abstract DirectionType resolveDirection(Road roadFrom, Road roadTo);

    public abstract int resolveExitId(Road road);

    public abstract Semaphore resolveSemaphore(int exitId, DirectionType direction);


    @Override
    public String getName() {
        String name = super.getName();
        return NAME_PREFIX.concat(name);
    }


    public void turnAllSemaphoresRed() {
        for (Semaphore sem : mSemaphores) {
            sem.setLight(Semaphore.Light.Red);
        }
    }
}
