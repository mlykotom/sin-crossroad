package Map;

public class SpawnPoint extends Place {
    public SpawnPoint(String name, int coordX, int coordY) {
        super(name, coordX, coordY);
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
}
