package com.apicatalog.rdf.primitive.flow;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfQuadSet;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTermFactory;
import com.apicatalog.rdf.primitive.TermHashMap;
import com.apicatalog.rdf.primitive.set.QuadSet;

public class QuadAcceptor implements RdfQuadConsumer, Supplier<RdfQuadSet> {

    protected final RdfTermFactory terms;
    protected final RdfQuadSet quadSet;
    protected Function<String, String> blankNodeIssuer;

    public QuadAcceptor() {
        this(new QuadSet(), new TermHashMap());
    }

    public QuadAcceptor(final RdfQuadSet quadSet) {
        this(quadSet, new TermHashMap());
    }

    public QuadAcceptor(final RdfTermFactory terms) {
        this(new QuadSet(), terms);
    }

    public QuadAcceptor(final RdfQuadSet quadSet, final RdfTermFactory terms) {
        this.quadSet = quadSet;
        this.terms = terms;
        this.blankNodeIssuer = Function.identity();
    }

    @Override
    public RdfQuadSet get() {
        return quadSet;
    }

    public QuadAcceptor quad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) {
        quadSet.add(terms.createQuad(subject, predicate, object, graph));
        return this;
    }

    public QuadAcceptor quad(RdfQuad quad) {
        quadSet.add(quad);
        return this;
    }

    @Override
    public QuadAcceptor quad(String subject, String predicate, String object, String datatype, String language, String direction, String graph) throws RdfConsumerException {
        final RdfTerm objectValue;
        if (language != null) {
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
            objectValue = getResource(object);
        }

        quad(getResource(subject),
                getResource(predicate),
                objectValue,
                Optional.ofNullable(graph)
                        .map(this::getResource)
                        .orElse(null));

        return this;
    }

    public RdfTermFactory terms() {
        return terms;
    }

    void blankNodeIssuer(Function<String, String> blankNodeIssuer) {
        if (blankNodeIssuer == null) {
            throw new IllegalArgumentException();
        }
        this.blankNodeIssuer = blankNodeIssuer;
    }
    
    protected final RdfResource getResource(final String name) {
        if (RdfQuadConsumer.isBlank(name)) {
            return terms.createBlankNode(blankNodeIssuer.apply(name.substring(2)));
        }
        return terms.createIRI(name);
    }
}
