package GUI.renderable;

import Map.CrossRoad;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class CrossRoadRenderable extends PlaceRenderable<CrossRoad> {

    public CrossRoadRenderable(CrossRoad place) {
        super(place);
    }


    @Override
    protected float getWidth(float cellSize) {
        return cellSize / 2;
    }


    @Override
    protected float getHeight(float cellSize) {
        return getWidth(cellSize);
    }

    @Override
    public void render(Graphics2D g2D, float cellSize) {
        float realX = getRealPositionX(cellSize);
        float realY = getRealPositionY(cellSize);

        Rectangle2D innerCross = new Rectangle2D.Float(
                realX, realY,
                getWidth(cellSize),
                cellSize / 2
        );


        drawShape(g2D, innerCross, Color.DARK_GRAY);
    }
}
