package com.apicatalog.rdf.fnc;

import com.apicatalog.rdf.RdfLiteral;
import com.apicatalog.rdf.RdfLiteral.Direction;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;

public class QuadEmitter {

    final RdfQuadConsumer consumer;

    public QuadEmitter(RdfQuadConsumer consumer) {
        this.consumer = consumer;
    }

    public QuadEmitter emit(RdfQuad quad) throws RdfConsumerException {
        emit(consumer, quad);
        return this;
    }

    public static void emit(RdfQuadConsumer consumer, RdfQuad quad) throws RdfConsumerException {

        if (quad.object().isLiteral()) {

            final RdfLiteral literal = quad.object().asLiteral();

            consumer.quad(
                    resource(quad.subject()),
                    resource(quad.predicate()),
                    literal.lexicalValue(),
                    literal.datatype(),
                    literal.language().orElse(null),
                    literal.direction()
                            .map(Direction::name)
                            .map(String::toLowerCase)
                            .orElse(null),
                    quad.graphName()
                            .map(RdfResource::value)
                            .orElse(null));
            return;
        }

        if (quad.object().isIRI() || quad.object().isBlankNode()) {
            consumer.quad(
                    resource(quad.subject()),
                    resource(quad.predicate()),
                    resource(quad.object().asResource()),
                    null,
                    null,
                    null,
                    quad.graphName()
                            .map(RdfResource::value)
                            .orElse(null));
            return;
        }

        if (quad.object().isTriple()) {
            throw new IllegalArgumentException("RDF triple terms are not supported, yet [" + quad + "].");
        }

        throw new IllegalStateException("An unknown object [" + quad + "].");
    }

    static final String resource(final RdfResource resource) {

        final String value = resource.value();

        return (resource.isBlankNode() && !RdfQuadConsumer.isBlank(value))
                ? "_:" + value
                : value;
    }
}
