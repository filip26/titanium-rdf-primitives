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

import com.apicatalog.rdf.model.RdfLiteral.Direction;

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
 * @see <a href="https://www.w3.org/TR/rdf12-concepts/#section-terms">RDF
 *      Terms</a>
 */
public interface RdfTerm {

    /**
     * A factory interface for creating RDF terms such as resources, literals,
     * triples, and quads.
     * <p>
     * This interface provides methods to create different types of RDF terms, which
     * are the building blocks of RDF statements (triples and quads). These methods
     * help in constructing RDF resources, literals, and structured statements
     * (triples and quads) that can be used in RDF graphs or datasets.
     */
    public interface Factory {

        /**
         * Creates a node identified with an absolute URI or blank node identifier.
         * 
         * @param id the value of the URI or the blank node identified
         * @return {@link RdfResource} identified by the id
         */
        RdfResource createResource(String id);

        /**
         * Creates a triple consisting of a subject, predicate, and object.
         * <p>
         * A triple is the core structure of RDF data, consisting of a subject,
         * predicate, and object.
         * 
         * @param subject   the subject of the triple, must be a {@link RdfResource}
         * @param predicate the predicate of the triple
         * @param object    the object of the triple, can be any {@link RdfTerm}
         * @return {@link RdfTriple} representing the RDF triple
         */
        RdfTriple createTriple(RdfResource subject, String predicate, RdfTerm object);

        /**
         * Creates a quad consisting of a subject, predicate, object, and graph.
         * <p>
         * A quad extends the triple structure by including an additional graph name,
         * allowing RDF data to be associated with a specific graph.
         * 
         * @param subject   the subject of the quad, must be a {@link RdfResource}
         * @param predicate the predicate of the quad
         * @param object    the object of the quad, can be any {@link RdfTerm}
         * @param graph     the graph name for the quad, must be a {@link RdfResource}
         * @return {@link RdfQuad} representing the RDF quad
         */
        RdfQuad createQuad(RdfResource subject, String predicate, RdfTerm object, RdfResource graph);

        /**
         * Creates an RDF literal with the specified lexical value and datatype.
         * <p>
         * RDF literals are used to represent values like strings, numbers, and dates.
         * 
         * @param lexicalValue the lexical value of the literal (e.g., "42", "hello")
         * @param datatype     the datatype of the literal (e.g., xsd:string, xsd:int)
         * @return {@link RdfLiteral} representing the RDF literal
         */
        RdfLiteral createLiteral(String lexicalValue, String datatype);

        /**
         * Creates an RDF language-tagged string with the specified lexical value,
         * datatype, and language tag.
         * <p>
         * Language-tagged strings are literals that also have a language tag, e.g.,
         * "hello"@en for English.
         * 
         * @param lexicalValue the lexical value of the language-tagged string
         * @param datatype     the datatype of the literal (should be
         *                     {@code rdf:langString})
         * @param langTag      the language tag (e.g., "en", "fr")
         * @param direction    the text direction of the string (LTR or RTL)
         * @return {@link RdfLiteral} representing the language-tagged string
         */
        RdfLiteral createLangString(String lexicalValue, String datatype, String langTag, Direction direction);

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
