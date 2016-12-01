package Behaviours.state;

import Common.DirectionType;
import Map.CrossRoadPlus;

import java.util.ArrayList;

public class CrossRoadStatus extends AgentStatus {
    // Lanes Structure
    // ExitId: 0 - Straight
    // ExitId: 0 - Left
    // ExitId: 1 - Straight
    // ExitId: 1 - Left
    // ExitId: 2 - Straight
    // ExitId: 2 - Left
    // ExitId: 3 - Straight
    // ExitId: 3 - Left
    public ArrayList<LaneState> lanes;


    public CrossRoadStatus(String agentId, CrossRoadPlus crossRoad) {
        super(agentId);

        lanes = new ArrayList<LaneState>(8);

        for(int i = 0; i < 4; i++) {
            LaneState lane = new LaneState();
            lane.carsCount = crossRoad.resolveExit(i, DirectionType.Straight).size();
            lane.semaphore = crossRoad.resolveSemaphore(i, DirectionType.Straight);
            lanes.add(lane);

            lane = new LaneState();
            lane.carsCount = crossRoad.resolveExit(i, DirectionType.Left).size();
            lane.semaphore = crossRoad.resolveSemaphore(i, DirectionType.Left);
            lanes.add(lane);
        }
    }
}

