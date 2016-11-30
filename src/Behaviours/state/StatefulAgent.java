package Behaviours.state;

import jade.core.Agent;


public abstract class StatefulAgent extends Agent {
    public abstract AgentState getCurrentState();


    @Override
    protected void setup() {
        super.setup();
        addBehaviour(new ReportStateBehaviour(this));
    }
}
