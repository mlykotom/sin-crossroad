package status;

import java.util.HashMap;


public class CrossRoadStatus extends BaseStatus {
    public final HashMap<String, Boolean> semaphoreStatus = new HashMap<>();


    public CrossRoadStatus(String agentID) {
        super(agentID);
    }
}
