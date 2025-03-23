package com.apicatalog.rdf.fnc;

import java.util.Optional;
import java.util.function.Supplier;

import com.apicatalog.rdf.RdfLiteral.Direction;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.RdfTermFactory;
import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.container.QuadDataHashSet;
import com.apicatalog.rdf.primitive.TermHashMap;

public class QuadDatasetProvider implements RdfQuadConsumer, Supplier<QuadDataHashSet> {

    final RdfTermFactory terms;
    final QuadDataHashSet dataset;

    public QuadDatasetProvider() {
        this(QuadDataHashSet.create(), new TermHashMap());
    }

    public QuadDatasetProvider(final QuadDataHashSet dataset) {
        this(dataset, new TermHashMap());
    }

    public QuadDatasetProvider(final RdfTermFactory terms) {
        this(QuadDataHashSet.create(), terms);
    }

    public QuadDatasetProvider(final QuadDataHashSet dataset, final RdfTermFactory terms) {
        this.dataset = dataset;
        this.terms = terms;
    }

    @Override
    public QuadDataHashSet get() {
        return dataset;
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
        dataset.add(terms.createQuad(subject, predicate, object, graph));
    }

    public RdfTermFactory terms() {
        return terms;
    }

    protected void quad(RdfQuad quad) {
        dataset.add(quad);
    }

    protected final RdfResource getResource(final String name) {
        if (RdfQuadConsumer.isBlank(name)) {
            return terms.createBlankNode(name);
        }
        return terms.createIRI(name);
    }
}
