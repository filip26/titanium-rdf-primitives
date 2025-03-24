package com.apicatalog.rdf.primitive.flow;

import java.util.Iterator;
import java.util.Optional;

import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfDataset;
import com.apicatalog.rdf.model.RdfGraph;
import com.apicatalog.rdf.model.RdfLiteral;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfQuadSet;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTriple;

public class QuadEmitter {

    protected final RdfQuadConsumer consumer;

    protected QuadEmitter(RdfQuadConsumer consumer) {
        this.consumer = consumer;
    }

    public static QuadEmitter create(RdfQuadConsumer consumer) {
        return new QuadEmitter(consumer);
    }

    public QuadEmitter emit(RdfDataset set) throws RdfConsumerException {
        emit(consumer, set);
        return this;
    }

    public QuadEmitter emit(RdfGraph graph, RdfResource graphName) throws RdfConsumerException {
        emit(consumer, graph, graphName);
        return this;
    }

    public QuadEmitter emit(RdfQuadSet set) throws RdfConsumerException {
        emit(consumer, set);
        return this;
    }

    public QuadEmitter emit(RdfQuad quad) throws RdfConsumerException {
        emit(consumer, quad);
        return this;
    }

    public QuadEmitter emit(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) throws RdfConsumerException {
        emit(consumer, subject, predicate, object, graph);
        return this;
    }

    public static void emit(RdfQuadConsumer consumer, RdfDataset set) throws RdfConsumerException {
        RdfGraph graph = set.defaultGraph();
        if (graph != null) {
            emit(consumer, graph, null);
        }

        for (RdfResource graphName : set.graphNames()) {
            emit(consumer,
                    set.namedGraph(graphName).orElseThrow(IllegalArgumentException::new),
                    graphName);
        }
    }

    public static void emit(RdfQuadConsumer consumer, RdfGraph graph, RdfResource graphName) throws RdfConsumerException {
        final Iterator<RdfTriple> it = graph.stream().iterator();
        while (it.hasNext()) {
            RdfTriple quad = it.next();
            emit(consumer, quad.subject(), quad.predicate(), quad.object(), graphName);
        }
    }

    public static void emit(RdfQuadConsumer consumer, RdfQuadSet set) throws RdfConsumerException {
        final Iterator<RdfQuad> it = set.stream().iterator();
        while (it.hasNext()) {
            RdfQuad quad = it.next();
            emit(consumer, quad);
        }
    }

    public static void emit(RdfQuadConsumer consumer, RdfQuad quad) throws RdfConsumerException {
        emit(consumer,
                quad.subject(),
                quad.predicate(),
                quad.object(),
                quad.graphName().orElse(null));
    }

    public static void emit(RdfQuadConsumer consumer, RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) throws RdfConsumerException {

        if (object.isLiteral()) {

            final RdfLiteral literal = object.asLiteral();

            consumer.quad(
                    resource(subject),
                    resource(predicate),
                    literal.lexicalValue(),
                    literal.datatype(),
                    literal.language().orElse(null),
                    literal.direction()
                            .map(Direction::name)
                            .map(String::toLowerCase)
                            .orElse(null),
                    Optional.ofNullable(graph)
                            .map(QuadEmitter::resource)
                            .orElse(null));
            return;
        }

        if (object.isResource()) {
            consumer.quad(
                    resource(subject),
                    resource(predicate),
                    resource(object.asResource()),
                    null,
                    null,
                    null,
                    Optional.ofNullable(graph)
                            .map(QuadEmitter::resource)
                            .orElse(null));
            return;
        }

        if (object.isTriple()) {
            throw new IllegalArgumentException("RDF triple terms are not supported, yet [" + subject + " " + predicate + " " + object + " " + graph + "].");
        }

        throw new IllegalStateException("An unknown object [" + subject + " " + predicate + " " + object + " " + graph + "].");
    }

    static String resource(final RdfResource resource) {

        final String value = resource.value();

        return (resource.isBlankNode() && !RdfQuadConsumer.isBlank(value))
                ? "_:" + value
                : value;
    }
}
