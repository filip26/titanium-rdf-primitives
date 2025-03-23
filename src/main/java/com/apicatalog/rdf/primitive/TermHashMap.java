package com.apicatalog.rdf.primitive;

import java.util.HashMap;

import com.apicatalog.rdf.RdfLiteral;
import com.apicatalog.rdf.RdfLiteral.Direction;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.RdfTermFactory;
import com.apicatalog.rdf.RdfTriple;

public class TermHashMap extends HashMap<String, RdfTerm> implements RdfTermFactory {

    private static final long serialVersionUID = 560481899503078382L;

    @Override
    public RdfResource createBlankNode(String value) {
        return (RdfResource) super.computeIfAbsent(
                Resource.key(value, true),
                key -> Resource.createBlankNode(value, key));
    }

    @Override
    public RdfResource createIRI(String value) {
        return (RdfResource) super.computeIfAbsent(
                Resource.key(value, false),
                key -> Resource.createIRI(value, key));
    }

    @Override
    public RdfTriple createTriple(RdfResource subject, RdfResource predicate, RdfTerm object) {
        return Triple.of(subject, predicate, object);
    }

    @Override
    public RdfQuad createQuad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) {
        return Quad.of(subject, predicate, object, graph);
    }

    @Override
    public RdfLiteral createLiteral(String lexicalValue, String datatype) {
        return (RdfLiteral) super.computeIfAbsent(
                Literal.key(lexicalValue, datatype, null, null),
                key -> Literal.of(lexicalValue, datatype, key));
    }

    @Override
    public RdfLiteral createLangString(String lexicalValue, String datatype, String langTag, Direction direction) {
        return (RdfLiteral) super.computeIfAbsent(
                Literal.key(lexicalValue, datatype, langTag, direction),
                key -> LangString.of(lexicalValue, datatype, langTag, direction, key));
    }
}
