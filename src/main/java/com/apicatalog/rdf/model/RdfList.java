package com.apicatalog.rdf.model;

import java.util.Map;
import java.util.stream.Stream;

/**
 * Represents an RDF List (rdf:List) data structure that holds an ordered
 * collection of RDF terms.
 * <p>
 * The {@link RdfList} interface provides methods for querying and interacting
 * with an RDF list. RDF lists are used to represent ordered collections of
 * terms in RDF, where each term can be another {@link RdfTerm}.
 * </p>
 */
public interface RdfList extends RdfTerm {

    boolean containsKey(RdfResource key);

    boolean containsValue(RdfTerm value);

    Stream<Map.Entry<RdfResource, RdfTerm>> stream();

    default RdfTerm put(RdfResource key, RdfTerm value) {
        throw new UnsupportedOperationException();
    }

    default RdfTerm remove(RdfResource key) {
        throw new UnsupportedOperationException();
    }

    @Override
    default boolean isList() {
        return true;
    }

    @Override
    default RdfList asList() {
        return this;
    }
}