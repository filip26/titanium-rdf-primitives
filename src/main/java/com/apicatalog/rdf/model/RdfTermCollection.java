package com.apicatalog.rdf.model;

import java.util.stream.Stream;

/**
 * Represents an RDF List (rdf:List) data structure that holds an ordered
 * collection of RDF terms.
 * <p>
 * The {@link RdfTermCollection} interface provides methods for querying and
 * interacting with an RDF list. RDF lists are used to represent ordered
 * collections of terms in RDF, where each term can be another {@link RdfTerm}.
 * </p>
 */
public interface RdfTermCollection extends RdfTerm {

    public enum Type {
        List
    }

    Type type();

    /**
     * Checks whether the collection contains the specified RDF term.
     * <p>
     * This method allows you to check for the presence of a specific
     * {@link RdfTerm} in the collection. The search is performed based on term
     * equality.
     * </p>
     *
     * @param term the {@link RdfTerm} to check for in the collection
     * @return {@code true} if the collection contains the specified term,
     *         {@code false} otherwise
     */
    boolean contains(RdfTerm term);

    /**
     * Returns a stream of all terms in the RDF list, in the order they appear.
     * <p>
     * This method provides a convenient way to iterate over the RDF list terms in a
     * functional style. It utilizes Java's Stream API, allowing for operations such
     * as filtering, mapping, and collecting.
     * </p>
     *
     * @return a {@link Stream} of {@link RdfTerm}s representing the terms in the
     *         RDF list
     */
    Stream<RdfTerm> stream();

    @Override
    default boolean isCollection() {
        return true;
    }

    @Override
    default RdfTermCollection asCollection() {
        return this;
    }
}