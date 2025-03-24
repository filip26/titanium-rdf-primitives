package com.apicatalog.rdf.fnc;

import java.util.Optional;
import java.util.function.Supplier;

import com.apicatalog.rdf.RdfLiteral.Direction;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfQuadSet;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.RdfTermFactory;
import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.container.QuadDataset;
import com.apicatalog.rdf.primitive.TermHashMap;

public class QuadSetProvider implements RdfQuadConsumer, Supplier<RdfQuadSet> {

    final RdfTermFactory terms;
    final RdfQuadSet quadSet;

    public QuadSetProvider() {
        this(QuadDataset.create(), new TermHashMap());
    }

    public QuadSetProvider(final RdfQuadSet quadSet) {
        this(quadSet, new TermHashMap());
    }

    public QuadSetProvider(final RdfTermFactory terms) {
        this(QuadDataset.create(), terms);
    }

    public QuadSetProvider(final RdfQuadSet quadSet, final RdfTermFactory terms) {
        this.quadSet = quadSet;
        this.terms = terms;
    }

    @Override
    public RdfQuadSet get() {
        return quadSet;
    }

    @Override
    public RdfQuadConsumer quad(String subject, String predicate, String object, String datatype, String language, String direction, String graph) throws RdfConsumerException {
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

    public void quad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) {
        quadSet.add(terms.createQuad(subject, predicate, object, graph));
    }

    public RdfTermFactory terms() {
        return terms;
    }

    protected void quad(RdfQuad quad) {
        quadSet.add(quad);
    }

    protected final RdfResource getResource(final String name) {
        if (RdfQuadConsumer.isBlank(name)) {
            return terms.createBlankNode(name);
        }
        return terms.createIRI(name);
    }
}
