package Map;

import javafx.util.Pair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public abstract class Place implements Serializable {
    protected String mName;
    protected List<Road> mRoads = new ArrayList<>();
    protected int mCoordX;
    protected int mCoordY;


    public Place(String name, int coordX, int coordY) {
        mName = name;
        mCoordX = coordX;
        mCoordY = coordY;
    }


    public String getName() {
        return mName;
    }


    protected void addRoads(Road... roads) {
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
