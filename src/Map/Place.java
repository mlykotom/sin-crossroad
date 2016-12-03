package Map;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


public class Place implements Serializable {
    protected String mName;
    protected List<Road> mRoads = new ArrayList<>();
    protected int mCoordX;
    protected int mCoordY;
    protected final UUID mId;


    public Place(String name, int coordX, int coordY) {
        mId = UUID.randomUUID();
        mName = name;
        mCoordX = coordX;
        mCoordY = coordY;
    }


    public UUID getId() {
        return mId;
    }


    public String getName() {
        return mName;
    }


    public void addRoads(Road... roads) {
        mRoads.addAll(Arrays.asList(roads));
    }


    public List<Road> getRoads() {
        return mRoads;
    }


    public int getCoordX() {
        return mCoordX;
    }


    public int getCoordY() {
        return mCoordY;
    }
}
