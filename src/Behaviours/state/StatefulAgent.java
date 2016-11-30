package Behaviours.state;

import jade.core.Agent;


public abstract class StatefulAgent extends Agent {
    public abstract AgentStatus getCurrentState();
}
