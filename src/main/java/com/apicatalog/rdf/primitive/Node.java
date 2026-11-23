package com.apicatalog.rdf.primitive;

import java.util.Collection;
import java.util.Map;

import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;

public record Node(
        String id,
        boolean isBlankNode,
        Map<String, Collection<RdfTerm>> properties) implements RdfResource {

}
