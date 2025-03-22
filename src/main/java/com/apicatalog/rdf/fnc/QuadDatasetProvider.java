package com.apicatalog.rdf.fnc;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.api.RdfConsumerException;
import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.primitive.LangString;
import com.apicatalog.rdf.primitive.Literal;
import com.apicatalog.rdf.primitive.Quad;
import com.apicatalog.rdf.primitive.QuadHashDataset;
import com.apicatalog.rdf.primitive.Resource;

public class QuadDatasetProvider implements RdfQuadConsumer, Supplier<QuadHashDataset>, Consumer<RdfQuad> {

    final Map<String, RdfResource> resources;
    final QuadHashDataset dataset;

    public QuadDatasetProvider() {
        this(QuadHashDataset.create(), new HashMap<>());
    }

    public QuadDatasetProvider(QuadHashDataset dataset) {
        this(dataset, new HashMap<>());
    }

    public QuadDatasetProvider(QuadHashDataset dataset, Map<String, RdfResource> resources) {
        this.dataset = dataset;
        this.resources = resources;
    }

    @Override
    public QuadHashDataset get() {
        return dataset;
    }

    @Override
    public void accept(RdfQuad quad) {
        dataset.add(quad);
    }

    @Override
    public RdfQuadConsumer quad(String subject, String predicate, String object, String datatype, String language, String direction, String graph) throws RdfConsumerException {
        final RdfTerm objectValue;
        if (language != null) {
            objectValue = LangString.of(object, datatype, language, direction);

        } else if (datatype != null) {
            objectValue = Literal.of(object, datatype);

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
        accept(Quad.of(subject, predicate, value, graph));
    }

    public Map<String, RdfResource> resources() {
        return resources;
    }

    protected final RdfResource getResource(final String name) {
        return name != null
                ? resources.computeIfAbsent(name, arg0 -> name.startsWith("_:") ? Resource.createBlankNode(name) : Resource.createIRI(name))
                : null;
    }
}
