package com.apicatalog.rdf.provider;

import java.util.function.Supplier;

import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfDataset;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTriple;
import com.apicatalog.rdf.primitive.Dataset;
import com.apicatalog.rdf.primitive.TermMap;

public class DatasetProvider implements RdfQuadConsumer, Supplier<RdfDataset> {

    protected final RdfTerm.Factory terms;
    protected final RdfDataset dataset;

    public DatasetProvider() {
        this(new Dataset(), new TermMap());
    }

    public DatasetProvider(final RdfDataset quads) {
        this(quads, new TermMap());
    }

    public DatasetProvider(final RdfTerm.Factory terms) {
        this(new Dataset(), terms);
    }

    public DatasetProvider(final RdfDataset quads, final RdfTerm.Factory terms) {
        this.dataset = quads;
        this.terms = terms;
    }

    @Override
    public RdfDataset get() {
        return dataset;
    }

    public DatasetProvider quad(RdfResource subject, String predicate, RdfTerm object, RdfResource graph) {
        return quad(terms.createTriple(subject, predicate, object), graph);
    }

    public DatasetProvider quad(RdfTriple triple, RdfResource graph) {
        dataset.add(graph, triple);
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
