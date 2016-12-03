package Map;

public class SpawnPoint extends Place {
    public static final String NAME_PREFIX = "SpawnPoint::";

    public static final int MIN_SPAWN_CAR_INTERVAL_MILLIS = 1000;
    public static final int MAX_SPAWN_CAR_INTERVAL_MILLIS = 10000;
    private final int mMinSpawnDelay;
    private final int mMaxSpawnDelay;


    public SpawnPoint(String name, int coordX, int coordY) {
        this(name, coordX, coordY, MIN_SPAWN_CAR_INTERVAL_MILLIS, MAX_SPAWN_CAR_INTERVAL_MILLIS);
    }


    @Override
    public String getName() {
        String name = super.getName();
        return NAME_PREFIX.concat(name);
    }


    /**
     * @param name
     * @param coordX
     * @param coordY
     * @param minSpawnDelay Minimum time to wait before spawning next car (in ms).
     * @param maxSpawnDelay Maximum time to wait before spawning next car (in ms).
     */
    public SpawnPoint(String name, int coordX, int coordY, int minSpawnDelay, int maxSpawnDelay) {
        super(name, coordX, coordY);
        mMinSpawnDelay = minSpawnDelay;
        mMaxSpawnDelay = maxSpawnDelay;
    }


    public void setRoad(Road road) {
        addRoads(road);
    }


    public Road getRoad() {
        if (mRoads.isEmpty()) {
            throw new IllegalArgumentException(String.format("SpawnPoint [%d,%d] has no road!", mCoordX, mCoordY));
        }
        return mRoads.get(0);
    }


    public int getMinSpawnDelay() {
        return mMinSpawnDelay;
    }


    public int getMaxSpawnDelay() {
        return mMaxSpawnDelay;
    }
}
