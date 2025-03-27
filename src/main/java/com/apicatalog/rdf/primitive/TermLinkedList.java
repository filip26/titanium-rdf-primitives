package com.apicatalog.rdf.primitive;

import java.util.LinkedList;
import java.util.stream.Stream;

import com.apicatalog.rdf.model.RdfList;
import com.apicatalog.rdf.model.RdfTerm;

public class TermLinkedList extends LinkedList<RdfTerm> implements RdfList {

    private static final long serialVersionUID = 8019281237783097797L;

    @Override
    public boolean contains(RdfTerm term) {
        return super.contains(term);
    }

    @Override
    public Stream<RdfTerm> stream() {
        return super.stream();
    }
}
