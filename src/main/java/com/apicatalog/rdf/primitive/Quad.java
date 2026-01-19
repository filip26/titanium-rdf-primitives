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
package com.apicatalog.rdf.primitive;

import java.util.Objects;

import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;

public record Quad(
        RdfResource subject,
        RdfResource predicate,
        RdfTerm object,
        RdfResource graph) implements RdfQuad {

    public Quad {
        subject = Objects.requireNonNull(subject, "Quad subject must not be null.");
        predicate = Objects.requireNonNull(predicate, "Quad predicate must not be null.");
        object = Objects.requireNonNull(object, "Quad object must not be null.");
        graph = Objects.requireNonNull(graph, "Quad graph must not be null.");
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append('[');

        Triple.printTriple(builder, subject, predicate, object);

        if (graph != null) {
            builder.append(',').append(graph);
        }

        return builder.append(']').toString();
    }
}
