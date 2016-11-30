package status;

import Map.Place;
import Map.Road;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;


/**
 * Created by adamj on 27.11.2016.
 */
public class CarStatus implements Serializable {
    @Nullable
    public final Place previousRoad;
    public String name;

    public Place sourcePlace;

    public Place destinationPlace;

    public long timestampStart;

    public long timestampEnd;

    public Road currentRoad;


    public CarStatus(String name, Place sourcePlace, Place destinationPlace, long timestampStart,
                     long timestampEnd, Road previousRoad, Road currentRoad) {
        this.name = name;
        this.sourcePlace = sourcePlace;
        this.destinationPlace = destinationPlace;
        this.timestampStart = timestampStart;
        this.timestampEnd = timestampEnd;
        this.previousRoad = previousRoad;
        this.currentRoad = currentRoad;
    }
}
