package com.apicatalog.rdf.model;

/**
 * Represents an RDF List (rdf:List) data structure that holds an ordered
 * collection of RDF terms.
 * <p>
 * The {@link RdfList} interface provides methods for querying and interacting
 * with an RDF list. RDF lists are used to represent ordered collections of
 * terms in RDF, where each term can be another {@link RdfTerm}.
 * </p>
 */
public interface RdfList extends RdfTermCollection {

    @Override
    default Type type() {
        return Type.List;
    }
    
    /**
     * Adds a term to the RDF list.
     * <p>
     * By default, this operation is unsupported and will throw an
     * {@link UnsupportedOperationException}. Implementations that allow
     * modifications to the RDF list should override this method to support adding
     * elements.
     * </p>
     *
     * @param term the {@link RdfTerm} to add to the list
     * @return {@code true} if the term was successfully added (implementation
     *         dependent)
     * @throws UnsupportedOperationException if modification of the list is not
     *                                       supported by the implementation
     */
    default boolean add(RdfTerm term) {
        throw new UnsupportedOperationException("Modification of RDF list is not supported by default.");
    }

    /**
     * Removes a term from the RDF list.
     * <p>
     * By default, this operation is unsupported and will throw an
     * {@link UnsupportedOperationException}. Implementations that support the
     * removal of terms should override this method to allow removing elements from
     * the list.
     * </p>
     *
     * @param term the {@link RdfTerm} to remove from the list
     * @return {@code true} if the term was successfully removed (implementation
     *         dependent)
     * @throws UnsupportedOperationException if removal of the term is not supported
     *                                       by the implementation
     */
    default boolean remove(RdfTerm term) {
        throw new UnsupportedOperationException("Modification of RDF list is not supported by default.");
    }
  
    @Override
    default RdfList asCollection() {
        return this;
    }
}