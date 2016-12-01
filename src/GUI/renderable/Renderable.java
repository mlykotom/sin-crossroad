package GUI.renderable;

import GUI.WorldMapCanvas;
import com.sun.istack.internal.Nullable;

import java.awt.*;


public abstract class Renderable {
    private static final Stroke NO_STROKE = new BasicStroke(0);
    private static final Paint DEFAULT_COLOR = Color.WHITE;
    protected Font mDebugFont = new Font("DebugText", Font.PLAIN, 10);


    public static float getCanvasPosition(int cell, float cellSize) {
        return (cell + WorldMapCanvas.FIXES_OUT_OF_CANVAS) * cellSize;
    }


    public abstract void render(Graphics2D g2D, float cellSize);


    protected void drawShape(Graphics2D g2D, Shape shape, Paint color, @Nullable Stroke stroke, @Nullable Paint borderColor) {
        g2D.setPaint(color);
        g2D.fill(shape);
        g2D.setStroke(stroke == null ? NO_STROKE : stroke);
        g2D.setPaint(borderColor == null ? DEFAULT_COLOR : borderColor);
        g2D.draw(shape);
    }


    protected void drawShape(Graphics2D g2D, Shape shape, Paint color) {
        drawShape(g2D, shape, color, null, null);
    }
}
