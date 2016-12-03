package GUI.renderable;

import Behaviours.state.CarStatus;
import Map.Road;

import java.awt.*;
import java.awt.geom.Path2D;


public class RoadRenderable extends PlaceRenderable<Road> {
    public static final int ROAD_KNOWN_MAX_PER_LENGTH = 5;
    public static final int LANE_OFFSET_TO_ITSELF = 8;

    protected long mCarsOnRoadThere = 0;
    protected long mCarsOnRoadBack = 0;
    private long mKnownMax;


    public RoadRenderable(Road road) {
        super(road);
        mKnownMax = mPlace.getLengthInPoints() * ROAD_KNOWN_MAX_PER_LENGTH;
    }


    public synchronized void setCar(CarStatus status) {
        if (mPlace.getPlaceA().getId().equals(status.sourcePlaceId)) {
            if (status.isEntered) {
                mCarsOnRoadThere++;
            } else {
                mCarsOnRoadThere--;
            }
        } else if (mPlace.getPlaceB().getId().equals(status.sourcePlaceId)) {
            if (status.isEntered) {
                mCarsOnRoadBack++;
            } else {
                mCarsOnRoadBack--;
            }
        } else {
            System.out.println("Unknown direction for road " + mPlace.getName());
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
    public void render(Graphics2D context) {
        float realStartX = getRealPositionX();
        float realStartY = getRealPositionY();
        float realEndX = getCanvasPosition(mPlace.getBCoordX(), mCellSize);
        float realEndY = getCanvasPosition(mPlace.getBCoordY(), mCellSize);

        float roadOffsetMargin = mCellSize / 10;

        Path2D wayThere = new Path2D.Float();
        Path2D wayBack = new Path2D.Float();

        float wayThereXStart, wayThereXEnd, wayThereYStart, wayThereYEnd;
        float wayBackXStart, wayBackXEnd, wayBackYStart, wayBackYEnd;

        // vertical road
        if (realStartY == realEndY) {
            float laneOffset = roadOffsetMargin * Math.signum(realEndX - realStartX);
            wayThereXStart = realStartX;
            wayThereXEnd = realEndX;
            wayThereYStart = realStartY + laneOffset;
            wayThereYEnd = realEndY + laneOffset;

            wayBackXStart = realStartX;
            wayBackXEnd = realEndX;
            wayBackYStart = realStartY - laneOffset;
            wayBackYEnd = realEndY - laneOffset;
        } else {
            float laneOffset = roadOffsetMargin * Math.signum(realEndY - realStartY);

            wayThereXStart = realStartX - laneOffset;
            wayThereXEnd = realEndX - laneOffset;
            wayThereYStart = realStartY;
            wayThereYEnd = realEndY;

            wayBackXStart = realStartX + laneOffset;
            wayBackXEnd = realEndX + laneOffset;
            wayBackYStart = realStartY;
            wayBackYEnd = realEndY;
        }

        wayThere.moveTo(wayThereXStart, wayThereYStart);
        wayThere.lineTo(wayThereXEnd, wayThereYEnd);
        wayBack.moveTo(wayBackXStart, wayBackYStart);
        wayBack.lineTo(wayBackXEnd, wayBackYEnd);


        // draws lanes
        context.setStroke(new BasicStroke(mCellSize / 6));
        context.setPaint(calculateGradient(mCarsOnRoadThere, mKnownMax));
        context.draw(wayThere);
        context.setPaint(calculateGradient(mCarsOnRoadBack, mKnownMax));
        context.draw(wayBack);

        drawNumber(context, Color.BLACK, mCarsOnRoadThere, wayThereXStart, wayThereYStart, wayThereXEnd, wayThereYEnd);
        drawNumber(context, Color.BLACK, mCarsOnRoadBack, wayBackXStart, wayBackYStart, wayBackXEnd, wayBackYEnd);
    }


    private static Paint calculateGradient(long number, long knownMax) {
        if (number > knownMax) {
            return Color.DARK_GRAY;
        }

        float t = (number / (float) knownMax);
        return new Color(t, 1 - t, 0);
    }


    protected float getRealPositionX() {
        return getCanvasPosition(mPlace.getCoordX(), mCellSize);
    }


    protected float getRealPositionY() {
        return getCanvasPosition(mPlace.getCoordY(), mCellSize);
    }
}
