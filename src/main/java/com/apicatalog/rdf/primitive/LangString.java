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

public class LangString extends Literal implements RdfLiteral {

    final String langTag;

    final Direction direction;

    LangString(String lexicalValue, String datatype, String langTag, Direction direction) {
        super(lexicalValue, datatype);
        this.langTag = langTag;
        this.direction = direction;
    }

    public static LangString of(String lexicalValue, String datatype, String langTag) {
        return of(lexicalValue, datatype, langTag, (Direction) null);
    }

    public static LangString of(String lexicalValue, String datatype, String langTag, String direction) {
        if (direction != null) {
            return new LangString(lexicalValue, datatype, langTag, Direction.valueOf(direction.toUpperCase()));
        }
        return of(lexicalValue, datatype, langTag, (Direction) null);
    }

    public static LangString of(String lexicalValue, String datatype, String langTag, Direction direction) {
        return new LangString(lexicalValue, datatype, langTag, direction);
    }

    @Override
    public Optional<String> language() {
        return Optional.ofNullable(langTag);
    }

    @Override
    public Optional<Direction> direction() {
        return Optional.ofNullable(direction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lexicalValue, datatype, langTag, direction);
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
            LangString other = (LangString) obj;
            return Objects.equals(lexicalValue, other.lexicalValue)
                    && Objects.equals(datatype, other.datatype)
                    && Objects.equals(langTag, other.langTag)
                    && Objects.equals(direction, other.direction);
        }
        if (!(obj instanceof RdfLiteral)) {
            return false;
        }
        RdfLiteral other = (RdfLiteral) obj;
        return Objects.equals(lexicalValue, other.lexicalValue())
                && Objects.equals(datatype, other.datatype())
                && Objects.equals(langTag, other.language().orElse(null))
                && Objects.equals(direction, other.direction().orElse(null));
    }

    @Override
    public String toString() {
        return NQuadsWriter.literal(
                lexicalValue,
                datatype,
                langTag,
                direction != null
                        ? direction.name().toLowerCase()
                        : null);
    }
}
