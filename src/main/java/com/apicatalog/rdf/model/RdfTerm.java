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
 * An RDF statement's value. Represents an absolute IRI or blank node identifier
 * or RDF literal or {@link RdfTriple} term (RDF 1.2).
 *
 */
public interface RdfTerm {

    /**
     * Indicates if the value type is RDF literal.
     *
     * @return <code>true</code> if the value type is literal, <code>false</code>
     *         otherwise.
     */
    default boolean isLiteral() {
        return false;
    }

    /**
     * Indicates if the value type is IRI or blank node identifier.
     *
     * @return <code>true</code> if the value type is blank node, <code>false</code>
     *         otherwise.
     */
    default boolean isResource() {
        return false;
    }

    /**
     * Indicates if the value is a triple term. Introduced in RDF1.2
     * 
     * @return <code>true</code> is the term value is a triple term
     */
    default boolean isTriple() {
        return false;
    }

    /**
     * Return the RdfValue as a RdfLiteral
     *
     * @return the RdfValue as a RdfLiteral
     * @throws ClassCastException if the RdfValue is not a RdfLiteral
     *
     */
    default RdfLiteral asLiteral() {
        throw new ClassCastException();
    }

    default RdfResource asResource() {
        throw new ClassCastException();
    }

    default RdfTriple asTriple() {
        throw new ClassCastException();
    }

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    @Override
    String toString();
}
