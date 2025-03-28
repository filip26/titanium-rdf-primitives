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

/**
 * Represents the value of an RDF statement.
 * <p>
 * An {@code RdfTerm} can be one of the following:
 * <ul>
 * <li>An absolute IRI or a blank node identifier ({@link RdfResource})</li>
 * <li>An RDF literal ({@link RdfLiteral})</li>
 * <li>An RDF triple, representing RDF-star (RDF 1.2) embedded triples
 * ({@link RdfTriple})</li>
 * </ul>
 * <p>
 * This interface provides type-checking methods to determine the specific kind
 * of RDF term, as well as casting methods that allow safe access to the
 * underlying representation.
 *
 * @see <a href="https://www.w3.org/TR/rdf11-concepts/#dfn-rdf-term">RDF 1.1
 *      Term</a>
 * @see <a href="https://www.w3.org/TR/rdf12-concepts/#section-rdf-star">RDF 1.2
 *      (RDF-star)</a>
 */
public interface RdfTerm {

    /**
     * Indicates whether this term is an RDF literal.
     *
     * @return {@code true} if the term is an {@link RdfLiteral}; {@code false}
     *         otherwise.
     */
    default boolean isLiteral() {
        return false;
    }

    /**
     * Indicates whether this term is a resource, which includes absolute IRIs and
     * blank node identifiers.
     *
     * @return {@code true} if the term is an {@link RdfResource}; {@code false}
     *         otherwise.
     */
    default boolean isResource() {
        return false;
    }

    /**
     * Indicates whether this term is an RDF triple.
     * <p>
     * RDF triples are supported for RDF-star (RDF 1.2) compatibility.
     *
     * @return {@code true} if the term is an {@link RdfTriple}; {@code false}
     *         otherwise.
     */
    default boolean isTriple() {
        return false;
    }

    /**
     * Indicates whether this term is a collection.
     *
     * @return {@code true} if the term is an {@link RdfList}; {@code false}
     *         otherwise.
     */
    default boolean isList() {
        return false;
    }

    /**
     * Returns this term as an {@link RdfLiteral}.
     *
     * @return the {@link RdfLiteral} representation of this term.
     * @throws ClassCastException if this term is not an {@link RdfLiteral}.
     */
    default RdfLiteral asLiteral() {
        throw new ClassCastException();
    }

    /**
     * Returns this term as an {@link RdfResource}.
     *
     * @return the {@link RdfResource} representation of this term.
     * @throws ClassCastException if this term is not an {@link RdfResource}.
     */
    default RdfResource asResource() {
        throw new ClassCastException();
    }

    /**
     * Returns this term as an {@link RdfTriple}.
     *
     * @return the {@link RdfTriple} representation of this term.
     * @throws ClassCastException if this term is not an {@link RdfTriple}.
     */
    default RdfTriple asTriple() {
        throw new ClassCastException();
    }

    /**
     * Returns this term as an {@link RdfList}.
     *
     * @return the {@link RdfList} representation of this term.
     * @throws ClassCastException if this term is not an {@link RdfList}.
     */
    default RdfList asList() {
        throw new ClassCastException();
    }

    /**
     * Compares this RDF term to the specified object. Implementations must override
     * this method to provide equality checks appropriate to the term type.
     *
     * @param o the object to compare with this term.
     * @return {@code true} if the specified object is equal to this term;
     *         {@code false} otherwise.
     */
    @Override
    boolean equals(Object o);

    /**
     * Returns a hash code value for this RDF term.
     *
     * @return a hash code consistent with {@link #equals(Object)}.
     */
    @Override
    int hashCode();

    /**
     * Returns a string representation of this RDF term.
     *
     * @return a string describing this RDF term.
     */
    @Override
    String toString();
}
