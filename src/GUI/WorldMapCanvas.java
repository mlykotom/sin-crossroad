package GUI;

import Agents.CarAgent;
import GUI.tmp_delete.CarRenderable;
import GUI.tmp_delete.CrossR;
import GUI.tmp_delete.Renderable;
import GUI.tmp_delete.RoadRenderable;
import Map.Place;
import Map.SpawnPoint;
import model.BaseWorld;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;


/**
 * TODO testing classes, put somewhere else, encapsulate with proper classes
 */
public class WorldMapCanvas extends JPanel {
    public int gridSize = 10; // TODO get from world
    private static final Color CANVAS_DEFAULT_COLOR = Color.WHITE;
    private final BaseWorld mWorld;
    private Dimension mCanvasSize;


    public WorldMapCanvas(BaseWorld world) {
        mCanvasSize = new Dimension(500, 500);
        mWorld = world;

        setBackground(CANVAS_DEFAULT_COLOR);
        setMinimumSize(mCanvasSize);
        setPreferredSize(mCanvasSize);
        setMaximumSize(mCanvasSize);
    }


    private static Graphics2D setupCanvas(Graphics graphics) {
        Graphics2D g2D = (Graphics2D) graphics;
        g2D.setBackground(CANVAS_DEFAULT_COLOR);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        return g2D;
    }


    HashMap<String, Renderable> renderables = new HashMap<>();


    void test() {
        renderables.put("CrossRoad 1", new CrossR(53, 50));
        renderables.put("Road 2", new RoadRenderable(0, 0, 0, 1));
        renderables.put("CrossRoad 3", new CrossR(30, 30));
        renderables.put(CarAgent.getAgentName(1), new CarRenderable(102, 205));
    }


    void renderGrid(Graphics2D context) {
        for (int row = 0; row < mCanvasSize.height; row += mCanvasSize.height / gridSize) {
            for (int col = 0; col < mCanvasSize.width; col += mCanvasSize.width / gridSize) {
                Ellipse2D point = new Ellipse2D.Float(col, row, 1, 1);
                context.setPaint(Color.GRAY);
                context.fill(point);
                context.draw(point);
            }
        }
    }


    private static class Ren {
        protected Place mPlace;


        public Ren(Place place) {
            mPlace = place;
        }


        public void render(Graphics2D context, int cellSize) {
            float realX = mPlace.getCoordX() * cellSize;
            float realY = mPlace.getCoordY() * cellSize;
            Ellipse2D point = new Ellipse2D.Float(realX, realY, 3, 3);
            context.setPaint(Color.RED);
            context.fill(point);
            context.draw(point);
        }
    }


    private void renderWorld(Graphics2D context) {
        mWorld.SpawnPoints.forEach(spawnPoint -> {
            new Ren(spawnPoint).render(context, mCanvasSize.width / gridSize);
        });
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D context = setupCanvas(g);

        renderGrid(context);
        renderWorld(context);


        renderables.forEach((name, renderable) -> {
            renderable.render(context);
        });
    }
}
