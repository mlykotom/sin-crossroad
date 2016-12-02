package GUI.renderable;

import Map.Place;

import java.awt.*;


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

    protected void drawNumber(Graphics2D context, Paint color, long numberOfCars, float realStartX, float realStartY, float realEndX, float realEndY) {
        context.setFont(mDebugFont);
        FontMetrics fm = context.getFontMetrics();

        String text = String.valueOf(numberOfCars);

        float x = realStartX + (realEndX - fm.stringWidth(text) - realStartX) / 2;
        float y = realStartY + (realEndY + fm.getHeight() / 2 - realStartY) / 2;

        context.setPaint(color);
        context.drawString(text, x, y);
    }

}
