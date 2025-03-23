package com.apicatalog.rdf.container;

import java.util.HashMap;
import java.util.Optional;
import java.util.Set;

import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfResource;

public final class GraphHashMap extends HashMap<RdfResource, RdfGraph> implements RdfDataset {

    private static final long serialVersionUID = -3974746697661193189L;

    RdfGraph defaultGraph;

    @Override
    public RdfGraph defaultGraph() {
        return defaultGraph;
    }

    public GraphHashMap defaultGraph(RdfGraph graph) {
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

    public GraphHashMap namedGraph(RdfResource graphName, RdfGraph graph) {
        super.put(graphName, graph);
        return this;
    }
}
