package com.apicatalog.rdf.primitive;

import java.util.Set;

import com.apicatalog.rdf.model.RdfResource;

public record Graph(
        String id,
        boolean isBlankNode,
        Set<Node> nodes,
        Set<Predicate> predicates) implements RdfResource {

}
