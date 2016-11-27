package Map;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by adamj on 22.11.2016.
 */
public class Place implements Serializable{
    public List<Road> Connections;

    Place()
    {
        Connections = new LinkedList<>();
    }
}
