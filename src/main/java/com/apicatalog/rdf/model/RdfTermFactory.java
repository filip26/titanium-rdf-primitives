package com.apicatalog.rdf.model;

import com.apicatalog.rdf.model.RdfLiteral.Direction;

/**
 * A factory interface for creating RDF terms such as resources, literals,
 * triples, and quads.
 * <p>
 * This interface provides methods to create different types of RDF terms, which
 * are the building blocks of RDF statements (triples and quads). These methods
 * help in constructing RDF resources, literals, and structured statements
 * (triples and quads) that can be used in RDF graphs or datasets.
 */
public interface RdfTermFactory {

    /**
     * Creates a blank node with the specified id.
     * <p>
     * A blank node is an anonymous resource that does not have an IRI.
     * 
     * @param id the blank node identified
     * @return {@link RdfResource} representing the blank node
     */
    RdfResource createVariable(String id);

    /**
     * Creates a node identified with an absolute URI.
     * 
     * @param uri the value of the URI 
     * @return {@link RdfResource} identified by the URI
     */
    RdfResource createResource(String uri);
    
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
