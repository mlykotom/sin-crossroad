package GUI.tmp_delete;

import GUI.WorldMapCanvas;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class CarRenderable extends Renderable {
    public int x;
    public int y;


    public CarRenderable(int x, int y) {
        this.x = x;
        this.y = y;
    }


    public static int CAR_WIDTH = 3;
    public static int CAR_HEIGHT = 3;


    @Override
    public void render(Graphics2D context) {
        Rectangle2D rect = new Rectangle2D.Float(x, y, CAR_WIDTH, CAR_HEIGHT);

        context.setPaint(new Color(0, 0, 0));
        context.fill(rect);
        context.draw(rect);
    }
}
