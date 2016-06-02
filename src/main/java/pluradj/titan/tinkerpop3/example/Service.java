package pluradj.titan.tinkerpop3.example;

import org.apache.tinkerpop.gremlin.driver.Client;
import org.apache.tinkerpop.gremlin.driver.Cluster;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Service implements AutoCloseable {

    /**
     * There typically needs to be only one Cluster instance in an application.
     */
    private Cluster cluster;

    /**
     * Use the Cluster instance to construct different Client instances (e.g. one for sessionless communication
     * and one or more sessions). A sessionless Client should be thread-safe and typically no more than one is
     * needed unless there is some need to divide connection pools across multiple Client instances. In this case
     * there is just a single sessionless Client instance used for the entire App.
     */
    private Client client;

    /**
     * Create Service as a singleton given the simplicity of App.
     */
    private static final Service INSTANCE = new Service();

    private Service() {
        try {
            cluster = Cluster.build(new File("conf/driver-settings.yaml")).create();
            client = cluster.connect();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Service getInstance() {
        return INSTANCE;
    }

    public List<String> findCreatorsOfSoftware(String softwareName) throws Exception {
        // it is very important from a performance perspective to parameterize queries
        Map params = new HashMap();
        params.put("n", softwareName);

        return client.submit("g.V().hasLabel('software').has('name',n).in('created').values('name')", params)
                .all().get().stream().map(r -> r.getString()).collect(Collectors.toList());
    }

    @Override
    public void close() throws Exception {
        client.close();
        cluster.close();
    }
}
