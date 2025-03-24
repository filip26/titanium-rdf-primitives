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
 * Represents an immutable RDF triple, consisting of a subject, predicate, and
 * object.
 * <p>
 * An {@code RdfTriple} models a single statement in an RDF graph. Each triple
 * is composed of:
 * <ul>
 * <li>A {@linkplain #subject() subject} that identifies what the statement is
 * about.</li>
 * <li>A {@linkplain #predicate() predicate} that indicates the relationship or
 * property.</li>
 * <li>An {@linkplain #object() object} that provides the value or target of the
 * statement.</li>
 * </ul>
 * <p>
 * This interface extends {@link RdfTerm} and provides methods to access each
 * component of the triple. It also identifies itself as an RDF triple via the
 * {@link #isTriple()} method.
 * <p>
 * Experimental support for RDF-star (RDF 1.2) allows the object of a triple to
 * be another {@link RdfTriple}.
 *
 * @see <a href="https://www.w3.org/TR/rdf11-concepts/#dfn-rdf-triple">RDF 1.1
 *      Triple</a>
 */
public interface RdfTriple extends RdfTerm {

    /**
     * Returns the subject of the triple.
     * <p>
     * The subject is either an absolute IRI or a blank node identifier, denoting
     * the resource being described by the statement.
     *
     * @return a {@link RdfResource} representing the subject of the triple; never
     *         {@code null}
     */
    RdfResource subject();

    /**
     * Returns the predicate of the triple.
     * <p>
     * The predicate is an absolute IRI that specifies the property or relationship
     * between the subject and the object of the triple.
     *
     * @return a {@link RdfResource} representing the predicate of the triple; never
     *         {@code null}
     */
    RdfResource predicate();

    /**
     * Returns the object of the triple.
     * <p>
     * The object can be:
     * <ul>
     * <li>An absolute IRI or blank node identifier ({@link RdfResource})</li>
     * <li>A literal value ({@link RdfLiteral})</li>
     * <li>Another {@link RdfTriple} (RDF 1.2 - experimental support)</li>
     * </ul>
     *
     * @return a {@link RdfTerm} representing the object of the triple; never
     *         {@code null}
     */
    RdfTerm object();

    @Override
    default boolean isTriple() {
        return true;
    }

    @Override
    default RdfTriple asTriple() {
        return this;
    }
}
