import com.thinkaurelius.titan.core.Cardinality
import com.thinkaurelius.titan.core.Multiplicity

// an init script that returns a Map allows explicit setting of global bindings.
def globals = [:]

// Generates the modern graph into an empty graph via LifeCycleHook.
// Note that the name of the key in the "global" map is unimportant.
globals << [hook : [
  onStartUp: { ctx ->
    ctx.logger.info("Loading 'modern' graph data, if necessary.")
    try {
        if (!graph.vertices().hasNext()) {
            // define the schema/indexes if necessary
            def mgmt = graph.openManagement()
            if (!mgmt.getVertexLabels().iterator().hasNext()) {
                def person = mgmt.makeVertexLabel("person").make()
                def software = mgmt.makeVertexLabel("software").make()
                def name = mgmt.makePropertyKey("name").dataType(String.class).cardinality(Cardinality.SINGLE).make()
                def age = mgmt.makePropertyKey("age").dataType(Integer.class).cardinality(Cardinality.SINGLE).make()
                def lang = mgmt.makePropertyKey("lang").dataType(String.class).cardinality(Cardinality.SINGLE).make()
                def knows = mgmt.makeEdgeLabel("knows").multiplicity(Multiplicity.MULTI).make()
                def created = mgmt.makeEdgeLabel("created").multiplicity(Multiplicity.MULTI).make()
                def weight = mgmt.makePropertyKey("weight").dataType(Double.class).cardinality(Cardinality.SINGLE).make()
                def personByName = mgmt.buildIndex("personByName", Vertex.class).addKey(name).indexOnly(person).buildCompositeIndex()
                def softwareByName = mgmt.buildIndex("softwareByName", Vertex.class).addKey(name).indexOnly(software).buildCompositeIndex()
            }
            mgmt.commit()

            // create the data if necessary
            if (!graph.vertices().hasNext()) {
                def marko = graph.addVertex(T.label, "person", "name", "marko", "age", 29)
                def vadas = graph.addVertex(T.label, "person", "name", "vadas", "age", 27)
                def lop = graph.addVertex(T.label, "software", "name", "lop", "lang", "java")
                def josh = graph.addVertex(T.label, "person", "name", "josh", "age", 32)
                def ripple = graph.addVertex(T.label, "software", "name", "ripple", "lang", "java")
                def peter = graph.addVertex(T.label, "person", "name", "peter", "age", 35)
                marko.addEdge("knows", vadas, "weight", 0.5d)
                marko.addEdge("knows", josh, "weight", 1.0d)
                marko.addEdge("created", lop, "weight", 0.4d)
                josh.addEdge("created", ripple, "weight", 1.0d)
                josh.addEdge("created", lop, "weight", 0.4d)
                peter.addEdge("created", lop, "weight", 0.2d)
                graph.tx().commit()
            }
        }
    } catch (Throwable th) {
        // just in case because Groovy loves eating exceptions
        th.printStackTrace()
    }
  }
] as LifeCycleHook]

// define the default TraversalSource to bind queries to - this one will be named "g".
globals << [g : graph.traversal()]
