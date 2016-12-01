package Map;

import Agents.CarAgent;
import Common.DirectionType;
import model.Semaphore;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by raven on 23.11.2016.
 * Crossroad of following shape:
 * | D3 |
 * --      --
 * A0      C2
 * --      --
 * | B1 |
 * <p>
 * Each exit (road) has 2 directions (to and from)
 * and each direction has 2 lanes. The left lane
 * leading to crossroad is for turning left and
 * the right lane is for driving straight or turning
 * right. Each lane leading to crossroad has a
 * semaphore.
 */
public class CrossRoadPlus extends CrossRoad {
    public static final int EXIT_A = 0;
    public static final int EXIT_A_LEFT = 1;
    public static final int EXIT_B = 2;
    public static final int EXIT_B_LEFT = 3;
    public static final int EXIT_C = 4;
    public static final int EXIT_C_LEFT = 5;
    public static final int EXIT_D = 6;
    public static final int EXIT_D_LEFT = 7;


    public CrossRoadPlus(String name, int coordX, int coordY) {
        super(name, coordX, coordY);

        for (int i = 0; i < 8; i++)
            mSemaphores.add(new Semaphore());
    }


    public void setRoads(Road roadA, Road roadB, Road roadC, Road roadD) {
        addRoads(roadA, roadB, roadC, roadD);
    }


    @Override
    public DirectionType resolveDirection(Road roadFrom, Road roadTo) {
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
    public int resolveExitId(Road road) {
        return getRoads().indexOf(road);
    }


    /**
     * @param exitId
     * @param direction
     * @return
     */
    public Semaphore resolveSemaphore(int exitId, DirectionType direction) {
        int semaphoreIndex = exitId * 2 + (direction == DirectionType.Left ? 1 : 0);
        return mSemaphores.get(semaphoreIndex);
    }
}
