package GUI;

import GUI.renderable.*;
import model.BaseWorld;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


public class WorldMapCanvas extends JPanel {
    public static final int FIXES_OUT_OF_CANVAS = 1;
    private int mGridSize;
    private static final Color CANVAS_DEFAULT_COLOR = Color.WHITE;
    private final BaseWorld mWorld;
    private int mCanvasSize;
    private float mCellSize;
    private GridRenderable mGrid;
    private List<Renderable> mPlaces = new ArrayList<>();


    public WorldMapCanvas(BaseWorld world) {
        mCanvasSize = 500;
        mGridSize = 10 + FIXES_OUT_OF_CANVAS; // TODO get from world
        mWorld = world;

        Dimension canvasDimension = new Dimension(mCanvasSize, mCanvasSize);
        setBackground(CANVAS_DEFAULT_COLOR);
        setMinimumSize(canvasDimension);
        setPreferredSize(canvasDimension);
        setMaximumSize(canvasDimension);
        setupPlaces();
    }


    private void setupPlaces() {
        mGrid = new GridRenderable(mGridSize);

        mWorld.Roads.stream()
                .map(RoadRenderable::new)
                .forEach(roadRenderable -> mPlaces.add(roadRenderable));

        mWorld.SpawnPoints.stream()
                .map(SpawnPointRenderable::new)
                .forEach(spawnPointRenderable -> mPlaces.add(spawnPointRenderable));

        mWorld.CrossRoads.stream()
                .map(CrossRoadRenderable::new)
                .forEach(crossRoadRenderable -> mPlaces.add(crossRoadRenderable));
    }


    private Graphics2D setupCanvas(Graphics graphics) {
        Graphics2D g2D = (Graphics2D) graphics;
        mCellSize = mCanvasSize / mGridSize;

        g2D.setBackground(CANVAS_DEFAULT_COLOR);
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        return g2D;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D context = setupCanvas(g);
        // render
        mPlaces.forEach(placeRenderable -> placeRenderable.render(context, mCellSize));
        mGrid.render(context, mCellSize);
    }
}