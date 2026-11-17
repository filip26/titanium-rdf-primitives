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

import java.util.Optional;
import java.util.Set;

/**
 * Represents an RDF Dataset.
 * <p>
 * An RDF Dataset is a collection of RDF graphs. It includes:
 * <ul>
 * <li>Exactly one default graph, the default graph does not have a name and may
 * be <code>null</code></li>
 * <li>Zero or more named graphs, each identified by an IRI or a blank node</li>
 * </ul>
 * <p>
 * This interface provides access to the default graph, the collection of named
 * graph identifiers, and allows retrieval of specific named graphs by their
 * names.
 * <p>
 * Implementations may or may not support modifications to the dataset.
 *
 * @see <a href="https://www.w3.org/TR/rdf12-concepts/#section-dataset">RDF
 *      Dataset</a>
 * @see RdfGraph
 */
public interface RdfDataset {

    /**
     * Returns the default graph of the dataset.
     * <p>
     * The default graph is the unnamed graph in the dataset. It contains RDF
     * triples that are not part of any named graph.
     *
     * @return an immutable {@link Set} of {@link RdfTriple} representing the
     *         default graph; or {@code null}
     */
    Set<RdfTriple> defaultGraph();

    /**
     * Returns the set of graph names for all named graphs in the dataset.
     * <p>
     * Each name is represented as a {@link RdfResource}, which can be an IRI or a
     * blank node identifier.
     *
     * @return an immutable {@link Set} of {@link RdfResource} graph names; empty if
     *         no named graphs are present.
     */
    Set<RdfResource> graphNames();

    /**
     * Returns the named graph corresponding to the provided graph name.
     * <p>
     * If the dataset does not contain a named graph with the specified name, an
     * empty {@link Optional} is returned.
     *
     * @param graphName the URI or blank node id identifying the named graph
     * @return an immutable {@link Set} containing the {@link RdfTriple} if present;
     *         otherwise, {@code null}
     */
    Set<RdfTriple> namedGraph(RdfResource graphName);

    /**
     * Replaces the default graph in the dataset with the specified graph.
     * <p>
     * By default, this operation is unsupported and throws an
     * {@link UnsupportedOperationException}. Implementations that support
     * modification of the dataset should override this method.
     *
     * @param graph the set of triples to be set as the new default graph
     * @throws UnsupportedOperationException if modification is not supported
     */
    default void defaultGraph(Set<RdfTriple> graph) {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds or replaces a named graph in the dataset.
     * <p>
     * Associates the specified {@link RdfGraph} with the given {@link RdfResource}
     * graph name. If a graph with the same name already exists, it will be
     * replaced.
     * <p>
     * By default, this operation is unsupported and throws an
     * {@link UnsupportedOperationException}. Implementations that support
     * modification of the dataset should override this method.
     *
     * @param graphName the {@link RdfResource} identifying the named graph
     * @param graph     the graph to associate with the specified name
     * @throws UnsupportedOperationException if modification is not supported
     * @throws NullPointerException          if {@code graphName} or {@code graph}
     *                                       is {@code null}
     */
    default void namedGraph(RdfResource graphName, Set<RdfTriple> graph) {
        throw new UnsupportedOperationException();
    }

    default void add(RdfResource graph, RdfTriple triple) {
        throw new UnsupportedOperationException();
    }
}
