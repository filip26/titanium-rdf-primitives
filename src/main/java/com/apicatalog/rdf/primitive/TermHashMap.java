package com.apicatalog.rdf.primitive;

import java.util.HashMap;

import com.apicatalog.rdf.model.RdfLiteral;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTermFactory;
import com.apicatalog.rdf.model.RdfTriple;

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
        return new Triple(subject, predicate, object);
    }

    @Override
    public RdfQuad createQuad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) {
        return new Quad(subject, predicate, object, graph);
    }

    @Override
    public RdfLiteral createLiteral(String lexicalValue, String datatype) {
        return new Literal(lexicalValue, datatype);
    }

    @Override
    public RdfLiteral createLangString(String lexicalValue, String datatype, String langTag, Direction direction) {
        return new LangString(lexicalValue, datatype, langTag, direction);
    }
}
