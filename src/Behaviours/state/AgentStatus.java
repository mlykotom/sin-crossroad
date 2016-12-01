package Behaviours.state;

import java.io.Serializable;


public abstract class AgentStatus implements Serializable {
    protected final String mAgentId;


    public AgentStatus(String agentId) {
        mAgentId = agentId;
    }


    public String getAgentId() {
        return mAgentId;
    }
}
