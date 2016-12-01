package Behaviours.state;

import model.Semaphore;

import java.io.Serializable;


public class LaneState implements Serializable {
    public Semaphore semaphore;
    public int carsCount;

}
