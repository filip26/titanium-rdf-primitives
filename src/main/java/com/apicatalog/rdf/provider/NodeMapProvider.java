package com.apicatalog.rdf.provider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.primitive.Node;
import com.apicatalog.rdf.primitive.TermMap;

public class NodeMapProvider implements RdfQuadConsumer, Supplier<Map<String, Node>> {

    protected final RdfTerm.Factory terms;
    protected final Map<String, Node> nodes;
    protected Function<String, String> blankNodeIssuer;

    public NodeMapProvider() {
        this(new HashMap<String, Node>(), new TermMap());
    }

    public NodeMapProvider(final Map<String, Node> nodes) {
        this(nodes, new TermMap());
    }

    public NodeMapProvider(final RdfTerm.Factory terms) {
        this(new HashMap<String, Node>(), terms);
    }

    public NodeMapProvider(final  Map<String, Node> nodes, final RdfTerm.Factory terms) {
        this.nodes = nodes;
        this.terms = terms;
        this.blankNodeIssuer = Function.identity();
    }

    @Override
    public Map<String, Node> get() {
        return nodes;
    }

//    public NodeMapProvider quad(RdfResource subject, String predicate, RdfTerm object, RdfResource graph) {
//        quadSet.add(terms.createQuad(subject, predicate, object, graph));
//        return this;
//    }
//
//    public NodeMapProvider quad(RdfQuad quad) {
//        quadSet.add(quad);
//        return this;
//    }

    @Override
    public NodeMapProvider quad(
            String subject,
            String predicate,
            String object,
            String datatype,
            String language,
            String direction,
            String graph) throws RdfConsumerException {

        final RdfTerm objectValue;

        if (language != null || direction != null) {
            objectValue = terms.createLangString(
                    object,
                    datatype,
                    language,
                    Optional.ofNullable(direction)
                            .map(String::toUpperCase)
                            .map(Direction::valueOf)
                            .orElse(null));

        } else if (datatype != null) {
            objectValue = terms.createLiteral(object, datatype);

        } else {
            objectValue = terms.createResource(object);
        }

//        quad(getNode(subject),
//                predicate,
//                objectValue,
//                Optional.ofNullable(graph)
//                        .map(this::getNode)
//                        .orElse(null));

        return this;
    }

    public RdfTerm.Factory terms() {
        return terms;
    }
}
