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

public record Literal(
        String lexicalValue,
        String datatype) implements RdfLiteral {

    @Override
    public String language() {
        return null;
    }

    @Override
    public Direction direction() {
        return null;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append(this.getClass().getSimpleName())
                .append('[')
                .append(lexicalValue)
                .append(',')
                .append(datatype)
                .append(']')
                .toString();
    }
}
