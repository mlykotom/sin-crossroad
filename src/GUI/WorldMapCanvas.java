package GUI;

import Behaviours.state.AgentStatus;
import Behaviours.state.CarStatus;
import Behaviours.state.CrossRoadStatus;
import GUI.renderable.*;
import model.BaseWorld;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


public class WorldMapCanvas extends JPanel {
    public static final int FIXES_OUT_OF_CANVAS = 1;
    private final ConcurrentHashMap<String, AgentStatus> mWorldStatus;
    private int mGridSize;
    private static final Color CANVAS_DEFAULT_COLOR = Color.WHITE;
    private final BaseWorld mWorld;
    private int mCanvasSize;
    private float mCellSize;
    private GridRenderable mGrid;
    private LinkedHashMap<UUID, Renderable> mPlaces = new LinkedHashMap<>();


    public WorldMapCanvas(BaseWorld world, ConcurrentHashMap<String, AgentStatus> worldStatus) {
        mCanvasSize = 500;
        mGridSize = 5 + FIXES_OUT_OF_CANVAS; // TODO get from world
        mWorld = world;
        mWorldStatus = worldStatus;

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
//        parseWorldState();
        mPlaces.forEach((uuid, renderable) -> renderable.render(context, mCellSize));
        mGrid.render(context, mCellSize);
    }


    private void parseWorldState() {
        mWorldStatus.forEach((agentName, agentStatus) -> {
            if (agentStatus instanceof CarStatus) {
                CarStatus carStatus = ((CarStatus) agentStatus);
                UUID roadId = carStatus.currentRoadId;
                RoadRenderable road = (RoadRenderable) mPlaces.get(roadId);
//                road.addCar(carStatus.sourcePlaceId);
            } else if (agentStatus instanceof CrossRoadStatus) {
                System.out.println("CrossRoad: " + ((CrossRoadStatus) agentStatus).getAgentId());
            }
        });
    }


    public void setCarStatus(CarStatus status) {
        RoadRenderable road = (RoadRenderable) mPlaces.get(status.currentRoad.getId());
        road.setCar(status);
    }
}