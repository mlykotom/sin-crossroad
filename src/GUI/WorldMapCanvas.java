package GUI;

import Agents.CarAgent;
import Agents.CrossRoadAgent;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

/**
 * TODO testing classes, put somewhere else, encapsulate with proper classes
 */
public class WorldMapCanvas extends JPanel {
    public static final int CANVAS_CELL_SIZE = 10;
    public static final Dimension CANVAS_SIZE = new Dimension(500, 500);
    private static final Color CANVAS_DEFAULT_COLOR = new Color(255, 255, 255);
    private static final float POINT_SIZE = 1;


    public WorldMapCanvas() {
        setBackground(CANVAS_DEFAULT_COLOR);
        setMinimumSize(CANVAS_SIZE);
        setPreferredSize(CANVAS_SIZE);
        setMaximumSize(CANVAS_SIZE);

        test();
    }

    private static Graphics2D setupCanvas(Graphics graphics) {
        Graphics2D g2D = (Graphics2D) graphics;
        g2D.setBackground(CANVAS_DEFAULT_COLOR);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        return g2D;
    }

    private abstract static class Renderable {
        public abstract void render(Graphics2D context);
    }

    private static class CrossR extends Renderable {
        public int x;
        public int y;

        public static final int WIDTH = 8;

        public CrossR(int x, int y) {
            this.x = (x / CANVAS_CELL_SIZE) * CANVAS_CELL_SIZE;
            this.y = (y / CANVAS_CELL_SIZE) * CANVAS_CELL_SIZE;
        }

        @Override
        public void render(Graphics2D context) {
            Rectangle2D rect = new Rectangle2D.Float(x, y, WIDTH, WIDTH);

            context.setPaint(Color.RED);
            context.fill(rect);
            context.draw(rect);
        }
    }

    private static class RoadRenderable extends Renderable {
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

    private static class CarRenderable extends Renderable {
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

    HashMap<String, Renderable> renderables = new HashMap<>();

    void test() {
        renderables.put("CrossRoad 1", new CrossR(53, 50));
        renderables.put("Road 2", new RoadRenderable(0, 0, 0, 1));
        renderables.put("CrossRoad 3", new CrossR(30, 30));
        renderables.put(CarAgent.getAgentName(1), new CarRenderable(102, 205));
    }

    void renderGrid(Graphics2D context) {
        for (int row = 0; row < CANVAS_SIZE.height; row += CANVAS_CELL_SIZE) {
            for (int col = 0; col < CANVAS_SIZE.width; col += CANVAS_CELL_SIZE) {
                Ellipse2D point = new Ellipse2D.Float(col, row, POINT_SIZE, POINT_SIZE);
                context.setPaint(Color.GRAY);
                context.fill(point);
                context.draw(point);
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D context = setupCanvas(g);

        renderGrid(context);

        renderables.forEach((name, renderable) -> {
            renderable.render(context);
        });
    }
}
