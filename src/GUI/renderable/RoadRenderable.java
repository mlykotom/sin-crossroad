package GUI.renderable;

import Map.Road;

import java.awt.*;
import java.awt.geom.Path2D;


public class RoadRenderable extends PlaceRenderable<Road> {
    public static final int ROAD_KNOWN_MAX_PER_LENGTH = 15;
    protected long mCarsOnRoad = 0;


    public RoadRenderable(Road road) {
        super(road);
    }


    public void addCar() {
        mCarsOnRoad++;
        System.out.println("Cars in road: " + mCarsOnRoad);
    }


    public void removeCar() {
        mCarsOnRoad--;
        if (mCarsOnRoad < 0) mCarsOnRoad = 0;
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
        context.setPaint(calculateGradient(mCarsOnRoad, mPlace.Length * ROAD_KNOWN_MAX_PER_LENGTH));
        context.draw(path2D);
    }


    private static Paint calculateGradient(long number, long knownMax) {
        float tWithinBounds = (number / (float) knownMax) % 1.0f;
        return new Color((int) (255 * tWithinBounds), (int) (255 * (1 - tWithinBounds)), 0);
    }


    protected float getRealPositionX(float cellSize) {
        return getCanvasPosition(mPlace.getCoordX(), cellSize);
    }


    protected float getRealPositionY(float cellSize) {
        return getCanvasPosition(mPlace.getCoordY(), cellSize);
    }
}
