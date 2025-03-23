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

import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfTerm;
import com.apicatalog.rdf.RdfTriple;

public class Triple implements RdfTriple {

    final RdfResource subject;

    final RdfResource predicate;

    final RdfTerm object;

    Triple(final RdfResource subject, final RdfResource predicate, final RdfTerm object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    @Override
    public RdfResource subject() {
        return subject;
    }

    @Override
    public RdfResource predicate() {
        return predicate;
    }

    @Override
    public RdfTerm object() {
        return object;
    }

    @Override
    public String toString() {
        return printTriple(new StringBuilder(), subject, predicate, object)
                .append(' ')
                .append('.')
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() == obj.getClass()) {
            Triple other = (Triple) obj;
            return Objects.equals(subject, other.subject)
                    && Objects.equals(predicate, other.predicate)
                    && Objects.equals(object, other.object);
        }
        if (!(obj instanceof RdfTriple)) {
            return false;
        }
        RdfTriple other = (RdfTriple) obj;
        return Objects.equals(subject, other.subject())
                && Objects.equals(predicate, other.predicate())
                && Objects.equals(object, other.object());
    }

    static final StringBuilder printTriple(StringBuilder builder, RdfResource subject, RdfResource predicate, RdfTerm object) {
        builder.append(subject)
                .append(' ')
                .append(predicate)
                .append(' ');

        if (object.isTriple()) {
            return printTripleTerm(builder, object.asTriple());
        }

        return builder.append(object);
    }

    static final StringBuilder printTripleTerm(StringBuilder builder, RdfTriple triple) {
        builder.append("<<( ");
        return printTriple(builder,
                triple.subject(),
                triple.predicate(),
                triple.object())
                .append(" )>>");
    }
}
