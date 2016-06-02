package pluradj.titan.tinkerpop3.example;

import org.apache.tinkerpop.gremlin.server.GremlinServer;
import org.apache.tinkerpop.gremlin.server.Settings;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.List;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Basic test class that demonstrates how to start and stop an embedded Gremlin Server instance in a test. Note that
 * the server instance is not started or stopped in a thread-safe manner, but typically this is acceptable for most
 * testing use cases.
 */
public class ServiceTest {
    private GremlinServer server;

    private static Service service = Service.getInstance();

    @Before
    public void setUp() throws Exception {
        startServer();
    }

    /**
     * Starts a new instance of Gremlin Server.
     */
    public void startServer() throws Exception {
        final InputStream stream = ServiceTest.class.getResourceAsStream("gremlin-server.yaml");
        this.server = new GremlinServer(Settings.read(stream));

        server.start().join();
    }

    @After
    public void tearDown() throws Exception {
        stopServer();
    }

    /**
     * Stops a current instance of Gremlin Server.
     */
    public void stopServer() throws Exception {
        server.stop().join();
    }

    @AfterClass
    public static void tearDownCase() throws Exception {
        service.close();
    }

    @Test
    public void shouldCreateGraph() throws Exception {
        List<String> result = service.findCreatorsOfSoftware("lop");
        // the results may come back in any order
        assertEquals(result.size(), 3);
        assertThat(result, hasItem("marko"));
        assertThat(result, hasItem("josh"));
        assertThat(result, hasItem("peter"));
    }
}
