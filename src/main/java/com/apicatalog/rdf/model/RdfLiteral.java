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
 * The {@link RdfLiteral} interface represents an immutable RDF literal.
 * <p>
 * RDF literals are values in the RDF data model that can have an optional
 * language tag and/or datatype. They are often used to represent strings,
 * numbers, dates, or other literal values in RDF.
 */
public interface RdfLiteral extends RdfTerm {

    /**
     * Enumeration representing the direction of text.
     */
    public enum Direction {
        LTR, // Left-to-right direction
        RTL, // Right-to-left direction
    }

    /**
     * Returns the lexical value of the literal.
     * <p>
     * The lexical value is the actual content or value of the literal as a string.
     * 
     * @return the lexical value of the literal, never {@code null}
     */
    String lexicalValue();

    /**
     * Returns the absolute IRI denoting the datatype of the literal.
     *
     * @return the datatype IRI of the literal, never {@code null}
     */
    String datatype();

    /**
     * Returns an optional language tag associated with the literal.
     * <p>
     * If the literal is a language-tagged string (e.g. rdf:langString), this method
     * returns the language tag; otherwise, it returns {@link Optional#empty()}.
     * 
     * @return an {@link Optional} containing the language tag if present, or
     *         {@link Optional#empty()} if not set
     */
    Optional<String> language();

    /**
     * Returns the direction of the literal, which can be either left-to-right (LTR)
     * or right-to-left (RTL).
     * 
     * @return an {@link Optional} containing the direction if present, or
     *         {@link Optional#empty()} if not set
     */
    Optional<Direction> direction();

    @Override
    default boolean isLiteral() {
        return true;
    }

    @Override
    default RdfLiteral asLiteral() {
        return this;
    }
}