package GUI.renderable;

import GUI.WorldMapCanvas;

import java.awt.*;


public abstract class Renderable {
    private static final Stroke NO_STROKE = new BasicStroke(0);
    private static final Paint DEFAULT_COLOR = Color.WHITE;
    protected float mCellSize;
    protected Font mDebugFont;


    public static float getCanvasPosition(int cell, float cellSize) {
        return (cell + WorldMapCanvas.FIXES_OUT_OF_CANVAS) * cellSize;
    }


    static Paint calculateGradient(long number, long knownMax) {
        if (number > knownMax) {
            return Color.MAGENTA;
        }

        float t = (number / (float) knownMax);
        return new Color(t, 1 - t, 0);
    }


    protected abstract void render(Graphics2D g2D);


    protected void drawShape(Graphics2D g2D, Shape shape, Paint color, Stroke stroke, Paint borderColor) {
        g2D.setPaint(color);
        g2D.fill(shape);
        g2D.setStroke(stroke == null ? NO_STROKE : stroke);
        g2D.setPaint(borderColor == null ? DEFAULT_COLOR : borderColor);
        g2D.draw(shape);
    }


    protected void drawShape(Graphics2D g2D, Shape shape, Paint color) {
        drawShape(g2D, shape, color, null, null);
    }


    public final void prepareAndRender(Graphics2D context, float cellSize) {
        mDebugFont = new Font("DebugText", Font.PLAIN, (int) (cellSize / 6));
        mCellSize = cellSize;
        render(context);
    }
}
