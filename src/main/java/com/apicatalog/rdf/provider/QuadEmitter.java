package com.apicatalog.rdf.provider;

import java.util.Optional;
import java.util.Set;

import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfDataset;
import com.apicatalog.rdf.model.RdfLiteral;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
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

    public QuadEmitter emit(Set<RdfTriple> graph, RdfResource graphName) throws RdfConsumerException {
        emit(consumer, graph, graphName);
        return this;
    }

//    public QuadEmitter emit(RdfQuadSet set) throws RdfConsumerException {
//        emit(consumer, set);
//        return this;
//    }

    public QuadEmitter emit(RdfQuad quad) throws RdfConsumerException {
        emit(consumer, quad);
        return this;
    }

    public QuadEmitter emit(RdfResource subject, String predicate, RdfTerm object, RdfResource graph)
            throws RdfConsumerException {
        emit(consumer, subject, predicate, object, graph);
        return this;
    }

    public static void emit(RdfQuadConsumer consumer, RdfDataset set) throws RdfConsumerException {
        Set<RdfTriple> graph = set.defaultGraph();
        if (graph != null) {
            emit(consumer, graph, null);
        }

        for (final var graphName : set.graphNames()) {
            emit(consumer,
                    set.namedGraph(graphName),
                    graphName);
        }
    }

    public static void emit(RdfQuadConsumer consumer, Set<RdfTriple> graph, RdfResource graphName)
            throws RdfConsumerException {
        for (var triple : graph) {
            emit(consumer, triple.subject(), triple.predicate(), triple.object(), graphName);
        }
    }

    public static void emit(RdfQuadConsumer consumer, Set<RdfQuad> quads) throws RdfConsumerException {
        for (var quad : quads) {
            emit(consumer, quad);
        }
    }

    public static void emit(RdfQuadConsumer consumer, RdfQuad quad) throws RdfConsumerException {
        emit(consumer,
                quad.subject(),
                quad.predicate(),
                quad.object(),
                quad.graph());
    }

    public static void emit(
            RdfQuadConsumer consumer,
            RdfResource subject,
            String predicate,
            RdfTerm object,
            RdfResource graph) throws RdfConsumerException {

        if (object instanceof RdfLiteral literal) {
            consumer.quad(
                    resource(subject),
                    predicate,
                    literal.lexicalValue(),
                    literal.datatype(),
                    literal.language(),
                    Optional.ofNullable(literal.direction())
                            .map(Direction::name)
                            .map(String::toLowerCase)
                            .orElse(null),
                    Optional.ofNullable(graph)
                            .map(QuadEmitter::resource)
                            .orElse(null));
            return;
        }

        if (object instanceof RdfResource resource) {
            consumer.quad(
                    resource(subject),
                    predicate,
                    resource(resource),
                    null,
                    null,
                    null,
                    Optional.ofNullable(graph)
                            .map(QuadEmitter::resource)
                            .orElse(null));
            return;
        }

        if (object instanceof RdfTriple) {
            throw new IllegalArgumentException("RDF triple terms are not supported, yet [" + subject + " " + predicate
                    + " " + object + " " + graph + "].");
        }

        throw new IllegalStateException(
                "An unknown object [" + subject + " " + predicate + " " + object + " " + graph + "].");
    }

    static String resource(final RdfResource resource) {

        final String value = resource.id();

        return (resource.isBlankNode() && !RdfQuadConsumer.isBlank(value))
                ? "_:" + value
                : value;
    }
}
