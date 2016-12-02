package model;

import java.awt.*;
import java.io.Serializable;


/**
 * Created by raven on 30.11.2016.
 */
public class Semaphore implements Serializable {
    public enum Light {
        Green(Color.GREEN),
        Red(Color.RED);

        public final Color color;


        Light(Color color) {
            this.color = color;
        }
    }


    private Light mLight;


    public Semaphore() {
        mLight = Light.Red;
    }


    public void setLight(Light light) {
        mLight = light;
    }


    public Light getLight() {
        return mLight;
    }
}
