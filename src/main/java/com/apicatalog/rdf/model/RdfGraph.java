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
package com.apicatalog.rdf.model;

import java.util.stream.Stream;

/**
 * Represents an RDF graph, a collection of RDF triples.
 * <p>
 * An RDF graph is a set of triples, where each triple consists of a subject,
 * predicate, and object, forming a statement of knowledge in the RDF format.
 * <p>
 * This interface defines the basic operations on an RDF graph, including
 * checking for the presence of a triple, adding and removing triples (although
 * these operations are unsupported by default), and streaming over the graph's
 * triples.
 */
public interface RdfGraph {

    /**
     * Checks if the RDF graph contains the specified triple.
     * <p>
     * This method allows you to verify whether a specific RDF triple is present in
     * the graph.
     *
     * @param triple the {@link RdfTriple} to check for
     * @return {@code true} if the graph contains the specified triple;
     *         {@code false} otherwise
     */
    boolean contains(RdfTriple triple);

    /**
     * Returns a stream of all the triples in the RDF graph.
     * <p>
     * This method provides a convenient way to iterate over all the triples in the
     * graph. It can be used with Java Streams API to process the triples in a
     * functional manner.
     *
     * @return a {@link Stream} of {@link RdfTriple}s representing the RDF graph
     */
    Stream<RdfTriple> stream();

    /**
     * Adds a triple to the RDF graph.
     * <p>
     * This operation is unsupported by default and will throw an
     * {@link UnsupportedOperationException}. Implementations that support
     * modification of the graph should override this method.
     *
     * @param triple the {@link RdfTriple} to add
     * @return {@code true} if the triple was successfully added (implementation
     *         dependent)
     * @throws UnsupportedOperationException if modification of the graph is not
     *                                       supported
     */
    default boolean add(RdfTriple triple) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes a triple from the RDF graph.
     * <p>
     * This operation is unsupported by default and will throw an
     * {@link UnsupportedOperationException}. Implementations that support removal
     * of triples should override this method.
     *
     * @param triple the {@link RdfTriple} to remove
     * @return {@code true} if the triple was successfully removed (implementation
     *         dependent)
     * @throws UnsupportedOperationException if removal of the triple is not
     *                                       supported
     */
    default boolean remove(RdfTriple triple) {
        throw new UnsupportedOperationException();
    }
}
