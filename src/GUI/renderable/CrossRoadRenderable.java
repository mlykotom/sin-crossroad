package GUI.renderable;

import Behaviours.state.CrossRoadStatus;
import Behaviours.state.LaneState;
import Map.CrossRoad;
import model.Semaphore;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


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
    public void render(Graphics2D g2D) {
        float realX = getRealPositionX();
        float realY = getRealPositionY();
        float width = getWidth(mCellSize);
        float height = getHeight(mCellSize);

        Rectangle2D innerCross = new Rectangle2D.Float(realX, realY, width, height);
        drawShape(g2D, innerCross, Color.DARK_GRAY);

        if (mStatus != null)
            drawAllSemaphores(g2D, realX, realY, width, height);
    }


    private void drawAllSemaphores(Graphics2D g2D, float realX, float realY, float width, float height) {
        if (mStatus == null) return;

        float semSize = width / 5;
        float correctOffset = semSize / 3;
        float semOffset;

        // left bottom
        semOffset = height / 4;
        drawSemaphores(g2D,
                2, 3,
                realX,
                realY + 3 * semOffset - correctOffset,
                realX,
                realY + 2 * semOffset - correctOffset,
                semSize
        );


        // right top
        drawSemaphores(g2D,
                6, 7,
                realX + width - semSize,
                realY + correctOffset,
                realX + width - semSize,
                realY + semOffset + correctOffset,
                semSize
        );


        // top left
        semOffset = width / 4;
        drawSemaphores(g2D,
                0, 1,
                realX + correctOffset,
                realY,
                realX + semOffset + correctOffset,
                realY,
                semSize
        );


        // bottom right
        drawSemaphores(g2D,
                4, 5,
                realX + 3 * semOffset-correctOffset,
                realY + height - semSize,
                realX + 2 * semOffset-correctOffset,
                realY + height - semSize,
                semSize
        );
    }


    private void drawSemaphores(Graphics2D g2D, int lStraight, int lLeft, float XStraight, float yStraight, float xLeft, float yLeft, float semSize) {
        LaneState laneStraight = mStatus.lanes.get(lStraight);
        LaneState laneLeft = mStatus.lanes.get(lLeft);

        Semaphore.Light lightStraight = laneStraight.semaphore.getLight();
        Semaphore.Light lightLeft = laneLeft.semaphore.getLight();

        Ellipse2D semaphoreStraight = new Ellipse2D.Float(XStraight, yStraight, semSize, semSize);
        drawShape(g2D, semaphoreStraight, laneStraight.semaphore.getLight().color);

        Ellipse2D semaphoreLeft = new Ellipse2D.Float(xLeft, yLeft, semSize, semSize);
        drawShape(g2D, semaphoreLeft, laneLeft.semaphore.getLight().color);

        drawNumber(g2D, lightStraight == Semaphore.Light.Red ? Color.WHITE : Color.BLACK, laneStraight.carsCount, XStraight, yStraight, XStraight + semSize, yStraight + semSize);
        drawNumber(g2D, lightLeft == Semaphore.Light.Red ? Color.WHITE : Color.BLACK, laneLeft.carsCount, xLeft, yLeft, xLeft + semSize, yLeft + semSize);
    }


    public void setStatus(CrossRoadStatus status) {
        mStatus = status;
    }
}