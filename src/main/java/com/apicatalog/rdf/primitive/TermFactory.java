package com.apicatalog.rdf.primitive;

import com.apicatalog.rdf.RdfLiteral;
import com.apicatalog.rdf.RdfLiteral.Direction;
import com.apicatalog.rdf.RdfQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.RdfTermFactory;
import com.apicatalog.rdf.RdfTriple;

public class TermFactory implements RdfTermFactory {

    
    public TermFactory() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public RdfResource createBlankNode(String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RdfResource createIRI(String value) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RdfTriple createTriple(RdfResource subject, RdfResource predicate, RdfTerm object) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RdfQuad createQuad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graph) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RdfLiteral createLiteral(String lexicalValue, String datatype) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public RdfLiteral createLangString(String lexicalValue, String datatype, String langTag, Direction direction) {
        // TODO Auto-generated method stub
        return null;
    }
    
    
    
}
