package GUI.renderable;

import Map.Place;

import java.util.UUID;


public abstract class PlaceRenderable<T extends Place> extends Renderable {
    protected final T mPlace;


    public PlaceRenderable(T place) {
        mPlace = place;
    }


    protected abstract float getWidth(float cellSize);

    protected abstract float getHeight(float cellSize);


    protected float getRealPositionX(float cellSize) {
        return getCanvasPosition(mPlace.getCoordX(), cellSize) - getWidth(cellSize) / 2;
    }


    protected float getRealPositionY(float cellSize) {
        return getCanvasPosition(mPlace.getCoordY(), cellSize) - getHeight(cellSize) / 2;
    }
}
