/*
 * Copyright 2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.apicatalog.rdf.primitive.set;

import java.util.LinkedHashSet;
import java.util.stream.Stream;

import com.apicatalog.rdf.model.RdfGraph;
import com.apicatalog.rdf.model.RdfTriple;

public final class OrderedTripleSet extends LinkedHashSet<RdfTriple> implements RdfGraph {

    private static final long serialVersionUID = 3719269032259156466L;

    @Override
    public Stream<RdfTriple> stream() {
        return super.stream();
    }

    @Override
    public boolean contains(RdfTriple triple) {
        return super.contains(triple);
    }

    @Override
    public boolean remove(RdfTriple triple) {
        return super.remove(triple);
    }
}
