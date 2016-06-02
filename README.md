Titan Server Application
========================

This is a starter project that demonstrates how a basic graph application can communicate with a Titan Server.

Titan implements the graph APIs defined in [Apache TinkerPop](http://tinkerpop.apache.org), so much of this example
code can work with any [Gremlin Server](http://tinkerpop.apache.org/docs/3.0.1-incubating/reference/#gremlin-server).
This project demonstrates how to connect to Gremlin Server through Java using the
[Gremlin Driver](http://tinkerpop.apache.org/docs/3.0.1-incubating/reference/#connecting-via-java) that is distributed
by TinkerPop.

Prerequisites
-------------

* Java 8 Update 40+
* [Apache Maven 3.x](https://maven.apache.org/)
* [Titan 1.0.0](https://titandb.io) (based on [Apache TinkerPop 3.0.1](http://tinkerpop.apache.org/docs/3.0.1-incubating/))

Building and Running
--------------------

Build the project as follows. Note: the unit test will launch a Gremlin Server on port `8182`, so make sure that you
do not have any active port conflicts (or run Maven with `-DskipTests`).

```
$ mvn clean package
```

Download and unzip Titan:

```
$ curl -O http://s3.thinkaurelius.com/downloads/titan/titan-1.0.0-hadoop1.zip
$ unzip titan-1.0.0-hadoop1.zip
$ cd titan-1.0.0-hadoop1
```

Clone this library and copy `generate-modern.groovy` into the Titan directory

```
$ git clone https://github.com/pluradj/titan-tp3-driver-example.git
$ cp /path/to/titan-tp3-driver-example/scripts/generate-modern.groovy /path/to/titan-1.0.0-hadoop1/scripts/
```

Edit the `gremlin-server.yaml` to use the `generate-modern.groovy` script

```
$ vim conf/gremlin-server/gremlin-server.yaml
// change empty-sample.groovy to generate-modern.groovy
```

Start Gremlin Server in the directory it was unzipped and specify the updated graph configuration file:

```
$ cd /path/to/titan-1.0.0-hadoop1
$ bin/gremlin-server.sh  ./conf/gremlin-server/gremlin-server.yaml

[INFO] GremlinServer -
         \,,,/
         (o o)
-----oOOo-(3)-oOOo-----

124  [main] INFO  org.apache.tinkerpop.gremlin.server.GremlinServer  - Configuring Gremlin Server from ./conf/gremlin-server/gremlin-server.yaml
193  [main] INFO  org.apache.tinkerpop.gremlin.server.util.MetricManager  - Configured Metrics ConsoleReporter configured with report interval=180000ms
195  [main] INFO  org.apache.tinkerpop.gremlin.server.util.MetricManager  - Configured Metrics CsvReporter configured with report interval=180000ms to fileName=/tmp/gremlin-server-metrics.csv
275  [main] INFO  org.apache.tinkerpop.gremlin.server.util.MetricManager  - Configured Metrics JmxReporter configured with domain= and agentId=
277  [main] INFO  org.apache.tinkerpop.gremlin.server.util.MetricManager  - Configured Metrics Slf4jReporter configured with interval=180000ms and loggerName=org.apache.tinkerpop.gremlin.server.Settings$Slf4jReporterMetrics
797  [main] INFO  com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration  - Set default timestamp provider MICRO
1121 [main] INFO  com.thinkaurelius.titan.graphdb.configuration.GraphDatabaseConfiguration  - Generated unique-instance-id=c0a8000c25660-hostname-local1
1150 [main] INFO  com.thinkaurelius.titan.diskstorage.Backend  - Initiated backend operations thread pool of size 16
1228 [main] INFO  com.thinkaurelius.titan.diskstorage.log.kcvs.KCVSLog  - Loaded unidentified ReadMarker start time 2016-06-02T20:12:38.795Z into com.thinkaurelius.titan.diskstorage.log.kcvs.KCVSLog$MessagePuller@3b7d3a38
1228 [main] INFO  org.apache.tinkerpop.gremlin.server.GremlinServer  - Graph [graph] was successfully configured via [conf/gremlin-server/titan-berkeleyje-server.properties].
1228 [main] INFO  org.apache.tinkerpop.gremlin.server.util.ServerGremlinExecutor  - Initialized Gremlin thread pool.  Threads in pool named with pattern gremlin-*
1517 [main] INFO  org.apache.tinkerpop.gremlin.groovy.engine.ScriptEngines  - Loaded nashorn ScriptEngine
1818 [main] INFO  org.apache.tinkerpop.gremlin.groovy.engine.ScriptEngines  - Loaded gremlin-groovy ScriptEngine
2348 [main] INFO  org.apache.tinkerpop.gremlin.groovy.engine.GremlinExecutor  - Initialized gremlin-groovy ScriptEngine with scripts/generate-modern.groovy
2348 [main] INFO  org.apache.tinkerpop.gremlin.server.util.ServerGremlinExecutor  - Initialized GremlinExecutor and configured ScriptEngines.
2356 [main] INFO  org.apache.tinkerpop.gremlin.server.util.ServerGremlinExecutor  - A GraphTraversalSource is now bound to [g] with graphtraversalsource[standardtitangraph[berkeleyje:db/berkeley], standard]
2439 [main] INFO  org.apache.tinkerpop.gremlin.server.AbstractChannelizer  - Configured application/vnd.gremlin-v1.0+gryo with org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV1d0
2439 [main] INFO  org.apache.tinkerpop.gremlin.server.AbstractChannelizer  - Configured application/vnd.gremlin-v1.0+gryo-stringd with org.apache.tinkerpop.gremlin.driver.ser.GryoMessageSerializerV1d0
2563 [main] INFO  org.apache.tinkerpop.gremlin.server.AbstractChannelizer  - Configured application/vnd.gremlin-v1.0+json with org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerGremlinV1d0
2564 [main] INFO  org.apache.tinkerpop.gremlin.server.AbstractChannelizer  - Configured application/json with org.apache.tinkerpop.gremlin.driver.ser.GraphSONMessageSerializerV1d0
2668 [gremlin-server-boss-1] INFO  org.apache.tinkerpop.gremlin.server.GremlinServer  - Gremlin Server configured with worker thread pool of 1, gremlin pool of 8 and boss thread pool of 1.
2668 [gremlin-server-boss-1] INFO  org.apache.tinkerpop.gremlin.server.GremlinServer  - Channel started at port 8182.```
```

Run this project as follows:

```
$ mvn exec:java -Dexec.mainClass="pluradj.titan.tinkerpop3.example.App" -Dlog4j.configuration=file:conf/log4j.properties

[INFO] Scanning for projects...
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] Building Getting started with Titan Server (Gremlin Server) 0.1
[INFO] ------------------------------------------------------------------------
[INFO]
[INFO] --- exec-maven-plugin:1.4.0:java (default-cli) @ driver-example ---
[INFO] pluradj.titan.tinkerpop3.example.App - Sending request....
[INFO] org.apache.tinkerpop.gremlin.driver.Connection - Created new connection for ws://localhost:8182/gremlin
[INFO] org.apache.tinkerpop.gremlin.driver.Connection - Created new connection for ws://localhost:8182/gremlin
[INFO] org.apache.tinkerpop.gremlin.driver.ConnectionPool - Opening connection pool on Host{address=localhost/127.0.0.1:8182, hostUri=ws://localhost:8182/gremlin} with core size of 2
[INFO] pluradj.titan.tinkerpop3.example.App -   - josh
[INFO] pluradj.titan.tinkerpop3.example.App -   - marko
[INFO] pluradj.titan.tinkerpop3.example.App -   - peter
[INFO] org.apache.tinkerpop.gremlin.driver.ConnectionPool - Signalled closing of connection pool on Host{address=localhost/127.0.0.1:8182, hostUri=ws://localhost:8182/gremlin} with core size of 2
[INFO] pluradj.titan.tinkerpop3.example.App - Service closed and resources released
```
