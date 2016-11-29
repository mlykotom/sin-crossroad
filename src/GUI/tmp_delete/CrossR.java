package GUI.tmp_delete;

import GUI.WorldMapCanvas;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class CrossR extends Renderable {
    public int x;
    public int y;

    public static final int WIDTH = 8;


    public CrossR(int x, int y) {
//        this.x = (x / WorldMapCanvas.CANVAS_CELL_SIZE) * WorldMapCanvas.CANVAS_CELL_SIZE;
//        this.y = (y / WorldMapCanvas.CANVAS_CELL_SIZE) * WorldMapCanvas.CANVAS_CELL_SIZE;
    }


    @Override
    public void render(Graphics2D context) {
        Rectangle2D rect = new Rectangle2D.Float(x, y, WIDTH, WIDTH);

        context.setPaint(Color.RED);
        context.fill(rect);
        context.draw(rect);
    }
}
