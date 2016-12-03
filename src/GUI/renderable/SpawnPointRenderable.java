package GUI.renderable;

import Map.SpawnPoint;

import java.awt.*;
import java.awt.geom.Ellipse2D;


public class SpawnPointRenderable extends PlaceRenderable<SpawnPoint> {
    public SpawnPointRenderable(SpawnPoint place) {
        super(place);
    }


    @Override
    protected float getWidth(float cellSize) {
        return cellSize / 2.5f;
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

        Ellipse2D ellipse2D = new Ellipse2D.Float(realX, realY, width, height);

        drawShape(g2D, ellipse2D, new Color(60, 158, 215));
//        drawNumber(g2D, Color.BLACK, 5, realX, realY, realX + width, realY + height);
    }
}
