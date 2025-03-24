package com.apicatalog.rdf.container;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import com.apicatalog.rdf.model.RdfDataset;
import com.apicatalog.rdf.model.RdfGraph;
import com.apicatalog.rdf.model.RdfResource;

public final class GraphDataset extends HashMap<RdfResource, RdfGraph> implements RdfDataset {

    private static final long serialVersionUID = -3974746697661193189L;

    RdfGraph defaultGraph;

    @Override
    public RdfGraph defaultGraph() {
        return defaultGraph;
    }

    public GraphDataset defaultGraph(RdfGraph graph) {
        this.defaultGraph = graph;
        return this;
    }

    @Override
    public Set<RdfResource> graphNames() {
        return super.keySet();
    }

    @Override
    public Optional<RdfGraph> namedGraph(RdfResource graphName) {
        return Optional.ofNullable(super.get(graphName));
    }

    public GraphDataset namedGraph(RdfResource graphName, RdfGraph graph) {
        super.put(graphName, graph);
        return this;
    }
}
