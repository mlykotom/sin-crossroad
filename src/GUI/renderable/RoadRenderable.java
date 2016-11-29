package GUI.renderable;

import Map.Road;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Random;


public class RoadRenderable extends PlaceRenderable<Road> {
    public RoadRenderable(Road road) {
        super(road);
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
        context.setPaint(new Color(new Random().nextInt()));
        context.draw(path2D);
    }


    protected float getRealPositionX(float cellSize) {
        return getCanvasPosition(mPlace.getCoordX(), cellSize);
    }


    protected float getRealPositionY(float cellSize) {
        return getCanvasPosition(mPlace.getCoordY(), cellSize);
    }
}
