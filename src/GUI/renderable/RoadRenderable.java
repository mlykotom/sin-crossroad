package GUI.renderable;

import Behaviours.state.CarStatus;
import Map.Road;

import java.awt.*;
import java.awt.geom.Path2D;
import java.util.UUID;


public class RoadRenderable extends PlaceRenderable<Road> {
    public static final int ROAD_KNOWN_MAX_PER_LENGTH = 10;
    protected long mCarsOnRoadThere = 0;
    protected long mCarsOnRoadBack = 0;
    private long mKnownMax;


    public RoadRenderable(Road road) {
        super(road);
        mKnownMax = mPlace.getLengthInPoints() * ROAD_KNOWN_MAX_PER_LENGTH;
    }


    public void addCar(UUID sourcePlaceId) {
        if (mPlace.getPlaceA().getId().equals(sourcePlaceId)) {
            mCarsOnRoadThere++;
        } else if (mPlace.getPlaceA().getId().equals(sourcePlaceId)) {
            mCarsOnRoadBack++;
        } else {
            System.out.println("Unknown direction for road " + mPlace.getName());
        }
    }


    public synchronized void setCar(CarStatus status) {
        if (mPlace.getPlaceA().getId().equals(status.sourcePlaceId)) {
            if (status.isEntered) {
                mCarsOnRoadThere++;
            } else {
                mCarsOnRoadThere--;
            }
        } else {
            // TODO what about 3rd option (unknown place start)
            if (status.isEntered) {
                mCarsOnRoadBack++;
            } else {
                mCarsOnRoadBack--;
            }
        }
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

        Path2D wayThere = new Path2D.Float();
        Path2D wayBack = new Path2D.Float();

        if (realStartX == realEndX) {
            wayThere.moveTo(realStartX - 8, realStartY);
            wayThere.lineTo(realEndX - 8, realEndY);
            wayBack.moveTo(realStartX + 8, realStartY);
            wayBack.lineTo(realEndX + 8, realEndY);
        } else {
            wayThere.moveTo(realStartX, realStartY - 8);
            wayThere.lineTo(realEndX, realEndY - 8);
            wayBack.moveTo(realStartX, realStartY + 8);
            wayBack.lineTo(realEndX, realEndY + 8);
        }

        context.setStroke(new BasicStroke(cellSize / 6));
        context.setPaint(calculateGradient(mCarsOnRoadThere, mKnownMax));
        context.draw(wayThere);

        context.setPaint(calculateGradient(mCarsOnRoadBack, mKnownMax));
        context.draw(wayBack);

        debugText(context, realStartX, realStartY, realEndX, realEndY);
    }


    private void debugText(Graphics2D context, float realStartX, float realStartY, float realEndX, float realEndY) {
        FontMetrics fm = context.getFontMetrics();
        String text = "(" + mCarsOnRoadThere + "," + mCarsOnRoadBack + ")";
        float x = realStartX + (realEndX - fm.stringWidth(text) - realStartX) / 2;
        float y = realStartY + (realEndY - realStartY) / 2;

        context.setFont(mDebugFont);
        context.setPaint(Color.BLACK);
        context.drawString(text, x, y);
    }


    private static Paint calculateGradient(long number, long knownMax) {
        if (number > knownMax) {
            return Color.DARK_GRAY;
        }

        int redParam = (int) (255 * (number / knownMax));
        int greenParam = 255 - redParam;
        return new Color(redParam, greenParam, 0); // TODO above knownmax turn black with gradient
    }


    protected float getRealPositionX(float cellSize) {
        return getCanvasPosition(mPlace.getCoordX(), cellSize);
    }


    protected float getRealPositionY(float cellSize) {
        return getCanvasPosition(mPlace.getCoordY(), cellSize);
    }
}
