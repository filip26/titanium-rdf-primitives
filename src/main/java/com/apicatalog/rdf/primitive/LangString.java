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

import com.apicatalog.rdf.model.RdfLiteral;

/**
 * Represents an RDF language-tagged string.
 * <p>
 * A {@link LangString} is an RDF literal that consists of a lexical value (the
 * value of the literal), a datatype, an optional language tag, and an optional
 * direction (left-to-right or right-to-left). It implements the
 * {@link RdfLiteral} interface and provides methods for retrieving the lexical
 * value, datatype, language tag, and direction of the language-tagged string.
 * 
 * @param lexicalValue the lexical value of the language-tagged string
 * @param datatype     the datatype of the literal (e.g.,
 *                     {@code rdf:langString})
 * @param language     the language tag (e.g., "en", "fr")
 * @param direction    the text direction (LTR or RTL)
 */
public record LangString(
        String lexicalValue,
        String datatype,
        String language,
        Direction direction) implements RdfLiteral {

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append('[')
                .append(lexicalValue)
                .append(',')
                .append(datatype)
                .append(',')
                .append(language)
                .append(',')
                .append(direction)
                .append(']')
                .toString();
    }
}
