package com.apicatalog.rdf;

import com.apicatalog.rdf.RdfLiteral.Direction;

public interface RdfTermFactory {

    RdfResource createBlankNode(String value);

    RdfResource createIRI(String value);

    RdfTriple createTriple(RdfResource subject, RdfResource predicate, RdfTerm object);

    RdfQuad createQuad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph);

    RdfLiteral createLiteral(String lexicalValue, String datatype);

    RdfLiteral createLangString(String lexicalValue, String datatype, String langTag, Direction direction);
}
