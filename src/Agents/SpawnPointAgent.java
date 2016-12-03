package Agents;

import Behaviours.state.AgentStatus;
import Behaviours.state.SpawnPointStatus;
import Behaviours.state.StatefulAgent;
import Behaviours.world.SpawnCarBehavior;
import Map.SpawnPoint;


public class SpawnPointAgent extends StatefulAgent {
    private SpawnPoint mSpawnPoint;
    private long mProducedCars = 0;


    @Override
    protected void setup() {
        super.setup();
        Object[] args = getArguments();
        mSpawnPoint = (SpawnPoint) args[0];
        addBehaviour(new SpawnCarBehavior(this, mSpawnPoint));
    }


    public void addProducedCar() {
        mProducedCars++;
    }


    @Override
    public AgentStatus getCurrentState() {
        return new SpawnPointStatus(getAID().getName(), mSpawnPoint, mProducedCars);
    }
}
