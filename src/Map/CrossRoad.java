package Map;

import Agents.CarAgent;
import Common.DirectionType;
import model.Semaphore;

import java.util.ArrayList;
import java.util.List;
public abstract class CrossRoad extends Place {
    protected List<Semaphore> mSemaphores = new ArrayList<>();

    public CrossRoad(String name, int coordX, int coordY) {
        super(name, coordX, coordY);
    }

    public abstract DirectionType resolveDirection(Road roadFrom, Road roadTo);

    public abstract int resolveExitId(Road road);

    public abstract Semaphore resolveSemaphore(int exitId, DirectionType direction);


    public void turnAllSemaphoresRed() {
        for (Semaphore sem : mSemaphores)
        {
            sem.setLight(Semaphore.Light.Red);
        }
    }
}
