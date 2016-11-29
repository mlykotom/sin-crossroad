package status;

import java.io.Serializable;


public class BaseStatus implements Serializable {
    public final String agentID;


    public BaseStatus(String agentID) {
        this.agentID = agentID;
    }
}
