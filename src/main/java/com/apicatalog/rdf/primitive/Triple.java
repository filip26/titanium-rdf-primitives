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

import com.apicatalog.rdf.model.RdfResource;
import com.apicatalog.rdf.model.RdfTerm;
import com.apicatalog.rdf.model.RdfTriple;

public record Triple(
        RdfResource subject,
        RdfResource predicate,
        RdfTerm object) implements RdfTriple {

    public Triple {
        subject = Objects.requireNonNull(subject, "Triple subject must not be null.");
        predicate = Objects.requireNonNull(predicate, "Triple predicate must not be null.");
        object = Objects.requireNonNull(object, "Triple object must not be null.");
    }

    @Override
    public String toString() {
        return printTriple(new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append('['),
                subject,
                predicate,
                object)
                .append(']')
                .toString();
    }

    static final StringBuilder printTriple(StringBuilder builder, RdfResource subject, RdfResource predicate,
            RdfTerm object) {
        builder.append(subject)
                .append(',')
                .append(predicate)
                .append(',');

        if (object.isTriple()) {
            return printTripleTerm(builder, object.asTriple());
        }

        return builder.append(object);
    }

    static final StringBuilder printTripleTerm(StringBuilder builder, RdfTriple triple) {
        return printTriple(builder.append('['),
                triple.subject(),
                triple.predicate(),
                triple.object())
                .append(']');
    }
}
