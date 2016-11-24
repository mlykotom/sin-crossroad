package Map;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by adamj on 22.11.2016.
 */
public class Place {
    public List<Road> Connections;

    Place()
    {
        Connections = new LinkedList<>();
    }
}
