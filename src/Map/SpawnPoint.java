package Map;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by raven on 23.11.2016.
 */
public class SpawnPoint extends Place {
    public String Name;

    SpawnPoint()
    {
        super();
    }

    SpawnPoint(String name)
    {
        super();
        Name = name;
    }

    public void SetRoad(Road road)
    {
        Connections = new LinkedList<>();
        Connections.add(road);
    }

    public Road Road()
    {
        return Connections.get(0);
    }
}
