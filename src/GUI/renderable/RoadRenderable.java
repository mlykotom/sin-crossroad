package GUI.renderable;

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
        mKnownMax = mPlace.Length * ROAD_KNOWN_MAX_PER_LENGTH;
    }


    public void addCar(UUID nextPlaceId) {
        if (mPlace.getId().equals(nextPlaceId)) {
            mCarsOnRoadThere++;
        } else {
            mCarsOnRoadBack++;
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


    Font debugFont = new Font("DebugText", Font.PLAIN, 10);


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

        mCarsOnRoadThere = 0;    // TODO must be proper way
        mCarsOnRoadBack = 0;    // TODO must be proper way
    }


    private void debugText(Graphics2D context, float realStartX, float realStartY, float realEndX, float realEndY) {
        FontMetrics fm = context.getFontMetrics();
        String text = "(" + mCarsOnRoadThere + "," + mCarsOnRoadBack + ")" + "/" + mKnownMax;
        float x = realStartX + (realEndX - fm.stringWidth(text) - realStartX) / 2;
        float y = realStartY + (realEndY - realStartY) / 2;

        context.setFont(debugFont);
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
