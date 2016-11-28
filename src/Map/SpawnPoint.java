package Map;

public class SpawnPoint extends Place {
    public SpawnPoint(String name, int coordX, int coordY) {
        super(name, coordX, coordY);
    }


    public void setRoad(Road road) {
        addRoads(road);
    }


    public Road getRoad() {
        return mRoads.get(0);
    }
}
