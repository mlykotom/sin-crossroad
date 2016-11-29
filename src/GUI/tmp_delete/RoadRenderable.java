package GUI.tmp_delete;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class RoadRenderable extends Renderable {
    int startX;
    int startY;
    int endX;
    int endY;


    public RoadRenderable(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }


    @Override
    public void render(Graphics2D context) {
        Rectangle2D rect = new Rectangle2D.Float(startX, startY, endX, endX);

        context.setPaint(Color.BLACK);
        context.fill(rect);
        context.draw(rect);
    }
}
