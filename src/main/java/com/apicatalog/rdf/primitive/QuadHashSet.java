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

import java.util.HashSet;
import java.util.stream.Stream;

import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfQuadSet;

public final class QuadHashSet extends HashSet<RdfQuad> implements RdfQuadSet {

    private static final long serialVersionUID = -3610909138877791762L;

    @Override
    public Stream<RdfQuad> stream() {
        return super.stream();
    }

    @Override
    public boolean contains(RdfQuad quad) {
        return super.contains((Object)quad);
    }

//    public QuadSet add(final RdfQuad quad) {
//
//        if (quad == null) {
//            throw new IllegalArgumentException();
//        }
//
//        final Optional<RdfResource> graphName = quad.graphName();
//
//        if (graphName.isPresent()) {
//
//            TripleSet graph = graphs.get(graphName.get());
//
//            if (graph == null) {
//
//                graph = new TripleSet();
//                graphs.put(graphName.get(), graph);
//                graph.add(quad);
//                nquads.add(quad);
//
//            } else if (!graph.contains(quad)) {
//
//                graph.add(quad);
//                nquads.add(quad);
//            }
//
//        } else {
//
//            // add to default graph
//            if (!defaultGraph.contains(quad)) {
//                defaultGraph.add(quad);
//                nquads.add(quad);
//            }
//        }
//        return this;
//    }
//
//    @Override
//    public int size() {
//        return nquads.size();
//    }

//    @Override
//    public RdfDataset add(RdfTriple triple) {
//
//        RdfQuad nquad = new Quad(triple.subject(), triple.predicate(), triple.object(), null);
//
//        if (!defaultGraph.contains(nquad)) {
//            defaultGraph.add(nquad);
//            nquads.add(nquad);
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
//    default RdfQuadSet add(RdfQuad quad) {
//        throw new UnsupportedOperationException();
//    }
//
//    default RdfQuadSet remove(RdfQuad quad) {
//        throw new UnsupportedOperationException();
//    }

}
