/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apicatalog.rdf.primitive;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.apicatalog.rdf.model.RdfDataset;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTriple;

public class Dataset implements RdfDataset {

    protected Set<RdfTriple> defaultGraph;

    /** named graphs index */
    protected final Map<RdfResource, Set<RdfTriple>> graphs;

    public Dataset() {
        this(Set.of(), new HashMap<>());
    }

    protected Dataset(Set<RdfTriple> defaultGraph, Map<RdfResource, Set<RdfTriple>> graphs) {
        this.graphs = graphs;
        this.defaultGraph = defaultGraph;
    }

    @Override
    public Set<RdfTriple> defaultGraph() {
        return defaultGraph;
    }

    @Override
    public void add(RdfResource graphName, RdfTriple triple) {

        Objects.requireNonNull(triple);

        // is named graph?
        if (graphName != null) {
            graphs.computeIfAbsent(graphName, key -> new HashSet<>()).add(triple);

        } else {
            // then it's default graph
            if (defaultGraph.isEmpty()) {
                defaultGraph = new HashSet<>();
            }

            // add to default graph
            defaultGraph.add(triple);
        }
    }

    @Override
    public Set<RdfResource> graphNames() {
        return graphs.keySet();
    }

    @Override
    public Set<RdfTriple> namedGraph(RdfResource graphName) {
        return graphs.get(graphName);
    }

    @Override
    public void defaultGraph(Set<RdfTriple> graph) {
        defaultGraph = new HashSet<>(graph);
    }

    @Override
    public void namedGraph(RdfResource graphName, Set<RdfTriple> graph) {
        graphs.put(graphName, new HashSet<>(graph));
    }

}
