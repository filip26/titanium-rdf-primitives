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
 * Represents a collection of RDF Quad statements.
 * <p>
 * An {@link RdfQuadSet} is a set of {@link RdfQuad} objects, which are
 * immutable N-Quad statements consisting of a subject, predicate, object, and
 * an optional graph name. This interface provides methods for querying the
 * presence of quads and streaming them.
 * <p>
 * Implementations of this interface may optionally support adding or removing
 * quads.
 */
public interface RdfQuadSet {

    /**
     * Checks if the set contains the specified {@link RdfQuad}.
     *
     * @param quad the {@link RdfQuad} to check
     * @return {@code true} if the set contains the specified quad, {@code false}
     *         otherwise
     */
    boolean contains(RdfQuad quad);

    /**
     * Returns a {@link Stream} of the RDF quads in this set.
     * <p>
     * The stream can be used for operations such as filtering or mapping over the
     * quads.
     *
     * @return a stream of {@link RdfQuad} objects in the set
     */
    Stream<RdfQuad> stream();

    /**
     * Adds {@link RdfQuad} to the RDF graph.
     * <p>
     * This operation is unsupported by default and will throw an
     * {@link UnsupportedOperationException}. Implementations that support
     * modification of the set should override this method.
     *
     * @param quad the {@link RdfQuad} to add
     * @return {@code true} if the quad was successfully added (implementation
     *         dependent)
     * @throws UnsupportedOperationException if modification of the set is not
     *                                       supported
     */
    default boolean add(RdfQuad quad) {
        throw new UnsupportedOperationException();
    }

    /**
     * Removes {@link RdfQuad} from the RDF graph.
     * <p>
     * This operation is unsupported by default and will throw an
     * {@link UnsupportedOperationException}. Implementations that support removal
     * of quads should override this method.
     *
     * @param quad the {@link RdfQuad} to remove
     * @return {@code true} if the quad was successfully removed (implementation
     *         dependent)
     * @throws UnsupportedOperationException if removal of the quad is not supported
     */
    default boolean remove(RdfQuad quad) {
        throw new UnsupportedOperationException();
    }
}
