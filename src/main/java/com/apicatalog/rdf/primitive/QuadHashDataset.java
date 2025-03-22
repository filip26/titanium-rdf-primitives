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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfQuadSet;
import com.apicatalog.rdf.RdfResource;

public class QuadHashDataset implements RdfDataset, RdfQuadSet {

    final TripleHashSet defaultGraph;
    /** named graphs index */
    final Map<RdfResource, TripleHashSet> graphs;
    /** all quads index */
    final Set<RdfQuad> quads;


    QuadHashDataset() {
        this.graphs = new HashMap<>();
        this.quads = new HashSet<>();
        this.defaultGraph = new TripleHashSet();
    }
    
    public static QuadHashDataset create() {
        return new QuadHashDataset();
    }

    @Override
    public RdfGraph defaultGraph() {
        return defaultGraph;
    }

    public RdfDataset add(final RdfQuad nquad) {

        if (nquad == null) {
            throw new IllegalArgumentException();
        }

        final Optional<RdfResource> graphName = nquad.graphName();

        if (graphName.isPresent()) {

            TripleHashSet graph = graphs.get(graphName.get());

            if (graph == null) {

                graph = new TripleHashSet();
                graphs.put(graphName.get(), graph);
                graph.add(nquad);
                quads.add(nquad);

            } else if (!graph.contains(nquad)) {

                graph.add(nquad);
                quads.add(nquad);
            }

        } else {

            // add to default graph
            if (!defaultGraph.contains(nquad)) {
                defaultGraph.add(nquad);
                quads.add(nquad);
            }
        }
        return this;
    }

    @Override
    public Set<RdfResource> graphNames() {
        return graphs.keySet();
    }

    @Override
    public Optional<RdfGraph> namedGraph(final RdfResource graphName) {
        return Optional.ofNullable(graphs.get(graphName));
    }

    @Override
    public int size() {
        return quads.size();
    }

//    @Override
//    public RdfDataset add(RdfTriple triple) {
//
//        RdfQuad nquad = new Quad(triple.subject(), triple.predicate(), triple.object(), null);
//
//        if (!defaultGraph.contains(nquad)) {
//            defaultGraph.add(nquad);
//            quads.add(nquad);
//        }
//
//        return this;
//    }
//    
//    /**
//     * Add Quad to the dataset.
//     *
//     * @param quad to add
//     * @return the same {@link RdfQuadSet} instance
//     */
//    public RdfQuadSet add(RdfQuad quad) {
//        throw new UnsupportedOperationException();
//    }

    public RdfQuadSet remove(RdfQuad quad) {
        quads.remove(quad);
        return this;
    }

    @Override
    public boolean contains(RdfQuad quad) {
        return quads.contains(quad);
    }

    @Override
    public Stream<RdfQuad> stream() {
        return quads.stream();
    }
}
