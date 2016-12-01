package GUI.renderable;

import Map.Road;

import java.awt.*;
import java.awt.geom.Path2D;


public class RoadRenderable extends PlaceRenderable<Road> {
    public static final int ROAD_KNOWN_MAX_PER_LENGTH = 10;
    protected long mCarsOnRoad = 0;
    private long mKnownMax;


    public RoadRenderable(Road road) {
        super(road);
        mKnownMax = mPlace.Length * ROAD_KNOWN_MAX_PER_LENGTH;
    }


    public void addCar() {
        mCarsOnRoad++;
    }


    public void removeCar() {
        mCarsOnRoad--;
        if (mCarsOnRoad < 0) mCarsOnRoad = 0;
    }


    public void setCars(long cars) {
        mCarsOnRoad = cars;
    }


    @Override
    protected float getWidth(float cellSize) {
        return 0;
    }


    @Override
    protected float getHeight(float cellSize) {
        return 0;
    }


    @Override
    public void render(Graphics2D context, float cellSize) {
        float realStartX = getRealPositionX(cellSize);
        float realStartY = getRealPositionY(cellSize);
        float realEndX = getCanvasPosition(mPlace.getBCoordX(), cellSize);
        float realEndY = getCanvasPosition(mPlace.getBCoordY(), cellSize);

        Path2D path2D = new Path2D.Float();
        path2D.moveTo(realStartX, realStartY);
        path2D.lineTo(realEndX, realEndY);

        context.setStroke(new BasicStroke(cellSize / 4));
        context.setPaint(calculateGradient(mCarsOnRoad, mKnownMax));
        context.draw(path2D);


        debugText(context, realStartX, realStartY, realEndX, realEndY);

        mCarsOnRoad = 0;    // TODO must be proper way
    }


    private void debugText(Graphics2D context, float realStartX, float realStartY, float realEndX, float realEndY) {
        FontMetrics fm = context.getFontMetrics();
        String text = mCarsOnRoad + "/" + mKnownMax;
        float x = realStartX + (realEndX - fm.stringWidth(text) - realStartX) / 2;
        float y = realStartY + (realEndY - realStartY) / 2;

        context.setPaint(Color.BLACK);
        context.drawString(text, x, y);
    }


    private static Paint calculateGradient(long number, long knownMax) {
        if (number > knownMax) {
            return Color.DARK_GRAY;
        }

        float tWithinBounds = (number / (float) knownMax) % 1.0f;
        float greenParam = 1 - tWithinBounds;
        return new Color((int) (255 * tWithinBounds), (int) (255 * greenParam), 0); // TODO above knownmax turn black with gradient
    }


    protected float getRealPositionX(float cellSize) {
        return getCanvasPosition(mPlace.getCoordX(), cellSize);
    }


    protected float getRealPositionY(float cellSize) {
        return getCanvasPosition(mPlace.getCoordY(), cellSize);
    }
}
