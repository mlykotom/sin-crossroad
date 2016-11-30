package model;

import java.io.Serializable;


/**
 * Created by raven on 30.11.2016.
 */
public class Semaphore implements Serializable {
    public enum Light {
        Green,
        Red
    }

    private Light mLight;

    public Semaphore()
    {
        mLight = Light.Red;
    }

    public void setLight(Light light)
    {
        mLight = light;
    }

    public Light getLight()
    {
        return mLight;
    }
}
