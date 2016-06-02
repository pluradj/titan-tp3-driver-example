package pluradj.titan.tinkerpop3.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        Service service = Service.getInstance();
        try {
            logger.info("Sending request....");

            // should print marko, josh and peter to the logs
            service.findCreatorsOfSoftware("lop").iterator().forEachRemaining(r -> logger.info(String.format("  - %s", r)));
        } catch (Exception ex) {
            logger.error("Could not execute traversal", ex);
        } finally {
            service.close();
            logger.info("Service closed and resources released");
        }
    }
}
