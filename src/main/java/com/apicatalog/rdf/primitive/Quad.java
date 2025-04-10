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
import java.util.Optional;

import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;

public class Quad extends Triple implements RdfQuad {

    final RdfResource graphName;

    Quad(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graphName) {
        super(subject, predicate, object);
        this.graphName = graphName;
    }

    public static RdfQuad of(RdfResource subject, RdfResource predicate, RdfTerm object, RdfResource graphName) {
        if (subject == null) {
            throw new IllegalArgumentException("Quad subject must not be null.");
        }
        if (predicate == null) {
            throw new IllegalArgumentException("Quad predicate must not be null.");
        }
        if (object == null) {
            throw new IllegalArgumentException("Quad object must not be null.");
        }
        return new Quad(subject, predicate, object, graphName);
    }

    @Override
    public Optional<RdfResource> graphName() {
        return Optional.ofNullable(graphName);
    }

    @Override
    public String toString() {

        final StringBuilder builder = new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append('[');

        printTriple(builder, subject, predicate, object);

        if (graphName != null) {
            builder.append(',').append(graphName);
        }

        return builder.append(']').toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object, graphName);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            Quad other = (Quad) obj;
            return Objects.equals(graphName, other.graphName);
        }
        if (!(obj instanceof RdfQuad)) {
            return graphName == null;
        }
        RdfQuad other = (RdfQuad) obj;
        return Objects.equals(graphName, other.graphName().orElse(null));
    }
}
