package GUI.renderable;

import java.awt.*;
import java.awt.geom.Ellipse2D;


public class GridRenderable extends Renderable {
    protected final int mGridSize;


    public GridRenderable(int gridSize) {
        mGridSize = gridSize;
    }

    @Override
    public void render(Graphics2D g2D) {
        for (int row = 0; row < mGridSize; row++) {
            for (int col = 0; col < mGridSize; col++) {
                Ellipse2D point = new Ellipse2D.Float(
                        getCanvasPosition(col, mCellSize),
                        getCanvasPosition(row, mCellSize),
                        1.5f,
                        1.5f
                );

                drawShape(g2D, point, Color.BLACK);
            }
        }
    }
}
