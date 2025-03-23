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
package com.apicatalog.rdf.container;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfGraph;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfQuadSet;
import com.apicatalog.rdf.RdfResource;

public final class QuadDataHashSet extends HashSet<RdfQuad> implements RdfDataset, RdfQuadSet {

    private static final long serialVersionUID = -1555324500576292374L;

    final RdfGraph defaultGraph;

    /** named graphs index */
    final Map<RdfResource, TripleHashSet> graphs;

    QuadDataHashSet() {
        this.graphs = new HashMap<>();
        this.defaultGraph = new TripleHashSet();
    }

    public static QuadDataHashSet create() {
        return new QuadDataHashSet();
    }

    @Override
    public RdfGraph defaultGraph() {
        return defaultGraph;
    }

    @Override
    public boolean add(final RdfQuad nquad) {

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
                return super.add(nquad);

            } else if (!graph.contains(nquad)) {

                graph.add(nquad);
                return super.add(nquad);
            }

        } else {

            // add to default graph
            if (!defaultGraph.contains(nquad)) {
                defaultGraph.add(nquad);
                return super.add(nquad);
            }
        }
        return false;
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
    public boolean contains(RdfQuad quad) {
        return super.contains(quad);
    }

    @Override
    public Stream<RdfQuad> stream() {
        return super.stream();
    }

    @Override
    public Iterator<RdfQuad> iterator() {

        Iterator<RdfQuad> it = super.iterator();

        return new Iterator<RdfQuad>() {

            RdfQuad last = null;

            @Override
            public RdfQuad next() {
                this.last = it.next();
                return last;
            }

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public void remove() {
                if (last.graphName().isPresent()) {
                    graphs.get(last.graphName().get()).remove(last);

                } else {
                    defaultGraph.remove(last);
                }

                it.remove();
            }
        };
    }

    @Override
    public boolean remove(RdfQuad quad) {
        return super.remove(quad);
    }
}
