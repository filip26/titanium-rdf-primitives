package com.apicatalog.rdf.provider;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfDataset;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTermFactory;
import com.apicatalog.rdf.model.RdfTriple;
import com.apicatalog.rdf.primitive.QuadDataset;
import com.apicatalog.rdf.primitive.TermHashMap;

public class DatasetProvider implements RdfQuadConsumer, Supplier<RdfDataset> {

    protected final RdfTermFactory terms;
    protected final RdfDataset dataset;
    protected Function<String, String> blankNodeIssuer;

    public DatasetProvider() {
        this(new QuadDataset(), new TermHashMap());
    }

    public DatasetProvider(final RdfDataset quads) {
        this(quads, new TermHashMap());
    }

    public DatasetProvider(final RdfTermFactory terms) {
        this(new QuadDataset(), terms);
    }

    public DatasetProvider(final RdfDataset quads, final RdfTermFactory terms) {
        this.dataset = quads;
        this.terms = terms;
        this.blankNodeIssuer = Function.identity();
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
    public DatasetProvider quad(
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
