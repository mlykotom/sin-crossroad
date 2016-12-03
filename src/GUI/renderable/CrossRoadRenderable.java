package GUI.renderable;

import Behaviours.state.CrossRoadStatus;
import Behaviours.state.LaneState;
import Map.CrossRoad;
import model.Semaphore;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;


public class CrossRoadRenderable extends PlaceRenderable<CrossRoad> {
    private int mKnownMaxInCrossRoad = 20;
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


    public void setStatus(CrossRoadStatus status) {
        mStatus = status;
    }


    @Override
    public void render(Graphics2D g2D) {
        if(mStatus == null)
            return;

        float width = getWidth(mCellSize);
        float height = getHeight(mCellSize);
        float strokeSize = width / 6;
        float realX = getRealPositionX();
        float realY = getRealPositionY();

        Rectangle2D innerCross = new Rectangle2D.Float(realX, realY, width, height);
        drawShape(g2D, innerCross, calculateGradient(mStatus.CarsInsideCount, mKnownMaxInCrossRoad), new BasicStroke(strokeSize), Color.DARK_GRAY);
        drawNumber(g2D, Color.BLACK, mStatus.CarsInsideCount, realX, realY, realX + width, realY + width);

        float corr = width / 8;
        drawAllSemaphores(g2D, realX - corr, realY - corr, width + 2 * corr, height + 2 * corr);
    }


    private void drawAllSemaphores(Graphics2D g2D, float realX, float realY, float width, float height) {
        if (mStatus == null) return;

        float semSize = width / 4.2f;
        float correctOffset = semSize / 3;
        float semOffset;

        semOffset = height / 4;
        // left bottom
        drawOneSemaphore(g2D, 2, realX, realY + 3 * semOffset - correctOffset, semSize);
        drawOneSemaphore(g2D, 3, realX, realY + 2 * semOffset - correctOffset, semSize);

        // right top
        drawOneSemaphore(g2D, 6, realX + width - semSize, realY + correctOffset, semSize);
        drawOneSemaphore(g2D, 7, realX + width - semSize, realY + semOffset + correctOffset, semSize);

        semOffset = width / 4;
        // top left
        drawOneSemaphore(g2D, 0, realX + correctOffset, realY, semSize);
        drawOneSemaphore(g2D, 1, realX + semOffset + correctOffset, realY, semSize);

        // bottom right
        drawOneSemaphore(g2D, 4, realX + 3 * semOffset - correctOffset, realY + height - semSize, semSize);
        drawOneSemaphore(g2D, 5, realX + 2 * semOffset - correctOffset, realY + height - semSize, semSize);
    }


    private void drawOneSemaphore(Graphics2D g2D, int index, float x, float y, float size) {
        LaneState lane = mStatus.lanes.get(index);
        Semaphore.Light light = lane.semaphore.getLight();

        Ellipse2D semaphore = new Ellipse2D.Float(x, y, size, size);
        drawShape(g2D, semaphore, light.color);

        drawNumber(g2D, light == Semaphore.Light.Red ? Color.CYAN : Color.BLACK, lane.carsCount, x, y, x + size, y + size);
    }
}