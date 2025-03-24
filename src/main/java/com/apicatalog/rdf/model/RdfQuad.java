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

/**
 * Represents an immutable N-Quad statement in the RDF data model.
 * <p>
 * An N-Quad extends the RDF triple by including an optional graph name, which
 * provides context or scoping for the triple. In contrast to a simple RDF
 * triple, which only consists of a subject, predicate, and object, an N-Quad
 * includes an additional graph name to specify the graph within which the
 * triple belongs.
 */
public interface RdfQuad extends RdfTriple {

    /**
     * Returns the optional graph name for this N-Quad statement.
     * <p>
     * If the N-Quad is part of a named graph, this method returns an
     * {@link Optional} containing the graph name. Otherwise, it returns
     * {@link Optional#empty()} if no graph name is specified.
     *
     * @return an {@link Optional} containing the graph name if present, or
     *         {@link Optional#empty()} if not set
     */
    Optional<RdfResource> graphName();

}
