package com.apicatalog.rdf.fnc;

import java.util.Iterator;
import java.util.Optional;

import com.apicatalog.rdf.RdfLiteral;
import com.apicatalog.rdf.RdfLiteral.Direction;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfQuadSet;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;

public final class QuadEmitter {

    final RdfQuadConsumer consumer;

    public QuadEmitter(RdfQuadConsumer consumer) {
        this.consumer = consumer;
    }

    public QuadEmitter emit(RdfQuadSet set) throws RdfConsumerException {
        emit(consumer, set);
        return this;
    }

    public static void emit(RdfQuadConsumer consumer, RdfQuadSet set) throws RdfConsumerException {
        final Iterator<RdfQuad> it = set.stream().iterator();
        while (it.hasNext()) {
            emit(consumer, it.next());
        }
    }

    public QuadEmitter emit(RdfQuad quad) throws RdfConsumerException {
        emit(consumer, quad);
        return this;
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
                            .map(RdfResource::value)
                            .orElse(null));
            return;
        }

        if (object.isIRI() || object.isBlankNode()) {
            consumer.quad(
                    resource(subject),
                    resource(predicate),
                    resource(object.asResource()),
                    null,
                    null,
                    null,
                    Optional.ofNullable(graph)
                            .map(RdfResource::value)
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
