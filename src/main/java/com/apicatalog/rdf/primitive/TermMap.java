package com.apicatalog.rdf.primitive;

import java.util.HashMap;
import java.util.Objects;
import java.util.function.Function;

import com.apicatalog.rdf.api.RdfQuadConsumer;
import com.apicatalog.rdf.model.RdfLiteral;
import com.apicatalog.rdf.model.RdfLiteral.Direction;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTriple;

public class TermMap extends HashMap<String, RdfTerm> implements RdfTerm.Factory {

    private static final long serialVersionUID = 560481899503078382L;

    protected Function<String, String> blankNodeIssuer = Function.identity();

    @Override
    public RdfResource createResource(String id) {

        if (RdfQuadConsumer.isBlank(id)) {
            return (RdfResource) super.computeIfAbsent(
                    blankNodeIssuer.apply(id.substring(2)),
                    key -> new Resource(key, true));
        }

        return (RdfResource) super.computeIfAbsent(
                id,
                key -> new Resource(key, false));
    }

    @Override
    public RdfTriple createTriple(RdfResource subject, String predicate, RdfTerm object) {
        return new Triple(subject, predicate, object);
    }

    @Override
    public RdfQuad createQuad(RdfResource subject, String predicate, RdfTerm object, RdfResource graph) {
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

    public void blankNodeIssuer(Function<String, String> blankNodeIssuer) {
        this.blankNodeIssuer = Objects.requireNonNull(blankNodeIssuer);
    }
}
