package com.apicatalog.rdf.provider;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTermFactory;
import com.apicatalog.rdf.primitive.TermHashMap;

public class QuadSetProvider implements RdfQuadConsumer, Supplier<Set<RdfQuad>> {

    protected final RdfTermFactory terms;
    protected final Set<RdfQuad> quas;
    protected Function<String, String> blankNodeIssuer;

    public QuadSetProvider() {
        this(new HashSet<>(), new TermHashMap());
    }

    public QuadSetProvider(final Set<RdfQuad> quads) {
        this(quads, new TermHashMap());
    }

    public QuadSetProvider(final RdfTermFactory terms) {
        this(new HashSet<>(), terms);
    }

    public QuadSetProvider(final Set<RdfQuad> quads, final RdfTermFactory terms) {
        this.quas = quads;
        this.terms = terms;
        this.blankNodeIssuer = Function.identity();
    }

    @Override
    public Set<RdfQuad> get() {
        return quas;
    }

    public QuadSetProvider quad(RdfResource subject, String predicate, RdfTerm object, RdfResource graph) {
        quas.add(terms.createQuad(subject, predicate, object, graph));
        return this;
    }

    public QuadSetProvider quad(RdfQuad quad) {
        quas.add(quad);
        return this;
    }

    @Override
    public QuadSetProvider quad(
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
            objectValue = getNode(object);
        }

        quad(getNode(subject),
                predicate,
                objectValue,
                Optional.ofNullable(graph)
                        .map(this::getNode)
                        .orElse(null));

        return this;
    }

    public RdfTermFactory terms() {
        return terms;
    }

    public void blankNodeIssuer(Function<String, String> blankNodeIssuer) {
        this.blankNodeIssuer = Objects.requireNonNull(blankNodeIssuer);
    }

    protected final RdfResource getNode(final String id) {
        if (RdfQuadConsumer.isBlank(id)) {
            return terms.createVariable(blankNodeIssuer.apply(id.substring(2)));
        }
        return terms.createResource(id);
    }
}
