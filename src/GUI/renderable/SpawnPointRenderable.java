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
    public void render(Graphics2D g2D, float cellSize) {
        Ellipse2D ellipse2D = new Ellipse2D.Float(
                getRealPositionX(cellSize),
                getRealPositionY(cellSize),
                getWidth(cellSize),
                getHeight(cellSize)
        );

        drawShape(g2D, ellipse2D, Color.RED);
    }
}
