package GUI.renderable;

import Map.CrossRoad;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;


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
        float width = getWidth(cellSize);
        float height = getHeight(cellSize);

        Rectangle2D innerCross = new Rectangle2D.Float(realX, realY, width, height);

        drawShape(g2D, innerCross, Color.DARK_GRAY);

        for (int i = 0; i < 4; i++) {
            int yFactor = i / 2;
            Ellipse2D semaphore = new Ellipse2D.Float(realX + (i % 2 * width), realY + yFactor * height, width / 4, width / 4);
            drawShape(g2D, semaphore, calculateSemaphoreColor(i));
        }
    }


    private Paint calculateSemaphoreColor(int i) {
        return new Random().nextBoolean() ? Color.RED : Color.GREEN;
    }
}
