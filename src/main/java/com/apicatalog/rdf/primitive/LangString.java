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

import com.apicatalog.rdf.model.RdfLiteral;

/**
 * Represents an RDF language-tagged string.
 * <p>
 * A {@link LangString} is an RDF literal that consists of a lexical value (the
 * value of the literal), a datatype, an optional language tag, and an optional
 * direction (left-to-right or right-to-left). It implements the
 * {@link RdfLiteral} interface and provides methods for retrieving the lexical
 * value, datatype, language tag, and direction of the language-tagged string.
 */
public class LangString extends Literal implements RdfLiteral {

    final String langTag;

    final Direction direction;

    LangString(String lexicalValue, String datatype, String langTag, Direction direction, String key) {
        super(lexicalValue, datatype, key);
        this.langTag = langTag;
        this.direction = direction;
    }

    /**
     * Factory method to create a {@link LangString} with the specified lexical
     * value, datatype, and language tag.
     * 
     * @param lexicalValue the lexical value of the language-tagged string
     * @param datatype     the datatype of the literal (e.g.,
     *                     {@code rdf:langString})
     * @param langTag      the language tag (e.g., "en", "fr")
     * @return a new {@link LangString} instance
     */
    public static LangString of(String lexicalValue, String datatype, String langTag) {
        return of(lexicalValue, datatype, langTag, (Direction) null);
    }

    /**
     * Factory method to create a {@link LangString} with the specified lexical
     * value, datatype, language tag, and direction.
     * 
     * @param lexicalValue the lexical value of the language-tagged string
     * @param datatype     the datatype of the literal (e.g.,
     *                     {@code rdf:langString})
     * @param langTag      the language tag (e.g., "en", "fr")
     * @param direction    the text direction (LTR or RTL)
     * @return a new {@link LangString} instance
     */
    public static LangString of(String lexicalValue, String datatype, String langTag, String direction) {
        if (direction != null) {
            return new LangString(lexicalValue, datatype, langTag, Direction.valueOf(direction.toUpperCase()), null);
        }
        return of(lexicalValue, datatype, langTag, (Direction) null, null);
    }

    /**
     * Factory method to create a {@link LangString} with the specified lexical
     * value, datatype, language tag, and direction.
     * 
     * @param lexicalValue the lexical value of the language-tagged string
     * @param datatype     the datatype of the literal (e.g.,
     *                     {@code rdf:langString})
     * @param langTag      the language tag (e.g., "en", "fr")
     * @param direction    the text direction (LTR or RTL)
     * @return a new {@link LangString} instance
     */
    public static LangString of(String lexicalValue, String datatype, String langTag, Direction direction) {
        return new LangString(lexicalValue, datatype, langTag, direction, null);
    }

    static LangString of(String lexicalValue, String datatype, String langTag, Direction direction, String key) {
        return new LangString(lexicalValue, datatype, langTag, direction, key);
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
        if (key == null) {
            key = key(lexicalValue, datatype, langTag, direction);
        }
        return key;
    }
}
