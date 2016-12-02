package GUI.renderable;

import Behaviours.state.CrossRoadStatus;
import Behaviours.state.LaneState;
import Map.CrossRoad;
import model.Car;
import model.Semaphore;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;


public class CrossRoadRenderable extends PlaceRenderable<CrossRoad> {

    private CrossRoadStatus mStatus;


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

        LaneState laneStraight, laneLeft;
        float semSize = width / 8, semOffset;

        // left bottom
        {
            semOffset = height / 4;
            laneStraight = mStatus.lanes.get(2);
            laneLeft = mStatus.lanes.get(3);

            Ellipse2D semaphoreStraight = new Ellipse2D.Float(realX, realY + 3 * semOffset, semSize, semSize);
            drawShape(g2D, semaphoreStraight, laneStraight.semaphore.getLight().color);

            Ellipse2D semaphoreLeft = new Ellipse2D.Float(realX, realY + 2 * semOffset, semSize, semSize);
            drawShape(g2D, semaphoreLeft, laneLeft.semaphore.getLight().color);
        }

        // right top
        {
            semOffset = height / 4;
            laneStraight = mStatus.lanes.get(6);
            laneLeft = mStatus.lanes.get(7);

            Ellipse2D semaphoreStraight = new Ellipse2D.Float(realX + width - semSize, realY, semSize, semSize);
            drawShape(g2D, semaphoreStraight, laneStraight.semaphore.getLight().color);

            Ellipse2D semaphoreLeft = new Ellipse2D.Float(realX + width - semSize, realY + semOffset, semSize, semSize);
            drawShape(g2D, semaphoreLeft, laneLeft.semaphore.getLight().color);
        }

        // top left
        {
            semOffset = width / 4;
            laneStraight = mStatus.lanes.get(0);
            laneLeft = mStatus.lanes.get(1);

            Ellipse2D semaphoreStraight = new Ellipse2D.Float(realX, realY, semSize, semSize);
            drawShape(g2D, semaphoreStraight, laneStraight.semaphore.getLight().color);

            Ellipse2D semaphoreLeft = new Ellipse2D.Float(realX + semOffset, realY, semSize, semSize);
            drawShape(g2D, semaphoreLeft, laneLeft.semaphore.getLight().color);
        }

        // bottom right
        {
            semOffset = width / 4;
            laneStraight = mStatus.lanes.get(3);
            laneLeft = mStatus.lanes.get(4);

            Ellipse2D semaphoreStraight = new Ellipse2D.Float(realX + 3 * semOffset, realY + height - semSize, semSize, semSize);
            drawShape(g2D, semaphoreStraight, laneStraight.semaphore.getLight().color);

            Ellipse2D semaphoreLeft = new Ellipse2D.Float(realX + 2 * semOffset, realY + height - semSize, semSize, semSize);
            drawShape(g2D, semaphoreLeft, laneLeft.semaphore.getLight().color);
        }

    }


    private Paint getSemaphoreColor(int i) {
        return new Random().nextBoolean() ? Color.RED : Color.GREEN;
    }


    public void setStatus(CrossRoadStatus status) {
        mStatus = status;
    }
}
