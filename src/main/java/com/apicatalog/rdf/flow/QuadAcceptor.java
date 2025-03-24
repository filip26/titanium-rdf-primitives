package com.apicatalog.rdf.flow;

import java.util.Optional;
import java.util.function.Supplier;

import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.container.QuadDataset;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfQuadSet;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTermFactory;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.primitive.TermHashMap;

public class QuadAcceptor implements RdfQuadConsumer, Supplier<RdfQuadSet> {

    protected final RdfTermFactory terms;
    protected final RdfQuadSet quadSet;

    public QuadAcceptor() {
        this(QuadDataset.create(), new TermHashMap());
    }

    public QuadAcceptor(final RdfQuadSet quadSet) {
        this(quadSet, new TermHashMap());
    }

    public QuadAcceptor(final RdfTermFactory terms) {
        this(QuadDataset.create(), terms);
    }

    public QuadAcceptor(final RdfQuadSet quadSet, final RdfTermFactory terms) {
        this.quadSet = quadSet;
        this.terms = terms;
    }

    @Override
    public RdfQuadSet get() {
        return quadSet;
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

    public QuadAcceptor quad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) {
        quadSet.add(terms.createQuad(subject, predicate, object, graph));
        return this;
    }

    public QuadAcceptor quad(RdfQuad quad) {
        quadSet.add(quad);
        return this;
    }

    public RdfTermFactory terms() {
        return terms;
    }

    protected final RdfResource getResource(final String name) {
        if (RdfQuadConsumer.isBlank(name)) {
            return terms.createBlankNode(name.substring(2));
        }
        return terms.createIRI(name);
    }
}
