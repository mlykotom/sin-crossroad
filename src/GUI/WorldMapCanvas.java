package GUI;

import Behaviours.state.CarStatus;
import Behaviours.state.CrossRoadStatus;
import Behaviours.state.SpawnPointStatus;
import GUI.renderable.*;
import model.BaseWorld;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.UUID;


public class WorldMapCanvas extends JPanel {
    public static final int FIXES_OUT_OF_CANVAS = 1;
    private int mGridSize;
    private static final Color CANVAS_DEFAULT_COLOR = Color.WHITE;
    private final BaseWorld mWorld;
    private int mCanvasSize;
    private float mCellSize;
    private GridRenderable mGrid;
    private LinkedHashMap<UUID, Renderable> mPlaces = new LinkedHashMap<>();


    public WorldMapCanvas(BaseWorld world) {
        mCanvasSize = 1200;
        mGridSize = world.getGridSize() + FIXES_OUT_OF_CANVAS;
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

        mWorld.Roads.forEach((uuid, road) -> mPlaces.put(uuid, new RoadRenderable(road)));
        mWorld.SpawnPoints.forEach((uuid, spawnPoint) -> mPlaces.put(uuid, new SpawnPointRenderable(spawnPoint)));
        mWorld.CrossRoads.forEach((uuid, crossRoad) -> mPlaces.put(uuid, new CrossRoadRenderable(crossRoad)));
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
        mGrid.prepareAndRender(context, mCellSize);
        mPlaces.forEach((uuid, renderable) -> renderable.prepareAndRender(context, mCellSize));
    }


    public synchronized void setCarStatus(CarStatus status) {
        RoadRenderable road = (RoadRenderable) mPlaces.get(status.currentRoadId);
        road.setCar(status);
    }


    public synchronized void setCrossRoadStatus(CrossRoadStatus status) {
        CrossRoadRenderable crossRoad = (CrossRoadRenderable) mPlaces.get(status.crossroadId);
        crossRoad.setStatus(status);
    }


    public synchronized void setSpawnPointStatus(SpawnPointStatus status) {
        SpawnPointRenderable place = (SpawnPointRenderable) mPlaces.get(status.spawnPointId);
        place.setStatus(status);
    }
}