package com.apicatalog.rdf.fnc;

import java.util.function.Supplier;

import com.apicatalog.rdf.RdfLiteral.Direction;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.RdfTermFactory;
import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.primitive.QuadHashDataset;
import com.apicatalog.rdf.primitive.TermFactory;

public class QuadDatasetProvider implements RdfQuadConsumer, Supplier<QuadHashDataset> {

    final RdfTermFactory terms;
    final QuadHashDataset dataset;

    public QuadDatasetProvider() {
        this(QuadHashDataset.create(), new TermFactory());
    }

    public QuadDatasetProvider(final QuadHashDataset dataset) {
        this(dataset, new TermFactory());
    }

    public QuadDatasetProvider(final RdfTermFactory terms) {
        this(QuadHashDataset.create(), terms);
    }

    public QuadDatasetProvider(final QuadHashDataset dataset, final RdfTermFactory terms) {
        this.dataset = dataset;
        this.terms = terms;
    }

    @Override
    public QuadHashDataset get() {
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
                    direction != null
                            ? Direction.valueOf(direction.toUpperCase())
                            : null);

        } else if (datatype != null) {
            objectValue = terms.createLiteral(object, datatype);

        } else {
            objectValue = getResource(object);
        }

        quad(getResource(subject),
                getResource(predicate),
                objectValue,
                getResource(graph));

        return this;
    }

    public void quad(RdfResource subject, RdfResource predicate, RdfTerm value, RdfResource graph) {
        dataset.add(terms.createQuad(subject, predicate, value, graph));
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
//        return name != null
//                ? resources.computeIfAbsent(name, arg0 -> name.startsWith("_:") ? Resource.createBlankNode(name) : Resource.createIRI(name))
//                : null;
    }
}
