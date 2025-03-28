package com.apicatalog.rdf.primitive;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

import com.apicatalog.rdf.model.RdfList;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;

public class TermLinkedList extends LinkedHashMap<RdfResource, RdfTerm> implements RdfList {

    private static final long serialVersionUID = 8019281237783097797L;

    @Override
    public boolean containsKey(RdfResource key) {
        return super.containsKey(key);
    }

    @Override
    public boolean containsValue(RdfTerm value) {
        return super.containsValue(value);
    }

    @Override
    public Stream<Map.Entry<RdfResource, RdfTerm>> stream() {
        return super.entrySet().stream();
    }

    @Override
    public RdfTerm put(RdfResource key, RdfTerm value) {
        return super.put(key, value);
    }

    @Override
    public RdfTerm remove(RdfResource key) {
        return super.remove(key);
    }
}
