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

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.apicatalog.rdf.model.RdfDataset;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTriple;

public final class QuadDataset implements RdfDataset {

    private Set<RdfTriple> defaultGraph;

    /** named graphs index */
    private Map<RdfResource, Set<RdfTriple>> graphs;

    public QuadDataset() {
        this.graphs = Map.of();
        this.defaultGraph = Set.of();
    }

    @Override
    public Set<RdfTriple> defaultGraph() {
        return defaultGraph;
    }

    @Override
    public void add(RdfResource graphName, RdfTriple triple) {

        Objects.requireNonNull(triple);

//        // is named graph?
//        if (graphName != null) {
//
//            var graph = graphs.get(graphName);
//
//            if (graph == null) {
//
//                graph = ;
//                graphs.put(graphName, graph);
//                graph.add(nquad);
//                return super.add(nquad);
//
//            } else if (!graph.contains(nquad)) {
//
//                graph.add(nquad);
//                return super.add(nquad);
//            }
//
//        } else {
//            // then it's default graph
//
//            if (!defaultGraph.contains(nquad)) {
//                // add to default graph
//                defaultGraph.add(nquad);
//                return super.add(nquad);
//            }
//        }
//        return false;
    }

    @Override
    public Set<RdfResource> graphNames() {
        return graphs.keySet();
    }

    @Override
    public Set<RdfTriple> namedGraph(RdfResource graphName) {
        return graphs.get(graphName);
    }
    
}
