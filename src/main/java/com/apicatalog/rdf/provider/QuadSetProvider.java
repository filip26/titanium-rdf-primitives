package com.apicatalog.rdf.provider;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.primitive.TermMap;

public class QuadSetProvider implements RdfQuadConsumer, Supplier<Set<RdfQuad>> {

    protected final RdfTerm.Factory terms;
    protected final Set<RdfQuad> quas;
    protected Function<String, String> blankNodeIssuer;

    public QuadSetProvider() {
        this(new HashSet<>(), new TermMap());
    }

    public QuadSetProvider(final Set<RdfQuad> quads) {
        this(quads, new TermMap());
    }

    public QuadSetProvider(final RdfTerm.Factory terms) {
        this(new HashSet<>(), terms);
    }

    public QuadSetProvider(final Set<RdfQuad> quads, final RdfTerm.Factory terms) {
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
    public void quad(
            String subject,
            String predicate,
            String object,
            String datatype,
            String language,
            String direction,
            String graph) {

        final RdfTerm objectValue;

        if (language != null || direction != null) {
            objectValue = terms.createLangString(
                    object,
                    datatype,
                    language,
                    direction != null
                            ? Direction.valueOf(direction.toUpperCase())
                            : null);

        } else if (datatype != null) {
            objectValue = terms.createLiteral(object, datatype);

        } else {
            objectValue = terms.createResource(object);
        }

        quad(terms.createResource(subject),
                predicate,
                objectValue,
                graph != null
                        ? terms.createResource(graph)
                        : null);
    }

    public RdfTerm.Factory terms() {
        return terms;
    }
}
