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

import com.apicatalog.rdf.RdfLiteral;
import com.apicatalog.rdf.nquads.NQuadsWriter;

public class Literal implements RdfLiteral {

    final String lexicalValue;

    final String datatype;

    Literal(String lexicalValue, String datatype) {
        this.lexicalValue = lexicalValue;
        this.datatype = datatype;
    }

    public static Literal of(String lexicalValue, String datatype) {
        return new Literal(lexicalValue, datatype);
    }

    @Override
    public boolean isLiteral() {
        return true;
    }

    @Override
    public String lexicalValue() {
        return lexicalValue;
    }

    @Override
    public String datatype() {
        return datatype;
    }

    @Override
    public Optional<String> language() {
        return Optional.empty();
    }

    @Override
    public Optional<Direction> direction() {
        return Optional.empty();
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexicalValue, datatype);
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
            Literal other = (Literal) obj;
            return Objects.equals(lexicalValue, other.lexicalValue)
                    && Objects.equals(datatype, other.datatype)
                    && !other.language().isPresent()
                    && !other.direction().isPresent();
        }
        if (!(obj instanceof RdfLiteral)) {
            return false;
        }
        RdfLiteral other = (RdfLiteral) obj;
        return Objects.equals(lexicalValue, other.lexicalValue())
                && Objects.equals(datatype, other.datatype())
                && !other.language().isPresent()
                && !other.direction().isPresent();
    }

    @Override
    public String toString() {
        return NQuadsWriter.literal(lexicalValue, datatype, null, null);
    }
}
