package com.apicatalog.rdf.primitive;

import java.util.Set;

import com.apicatalog.rdf.model.RdfResource;

public record Predicate(
        String id,
        Set<Node> subjects) implements RdfResource {

    @Override
    public boolean isBlankNode() {
        return false;
    }
}
