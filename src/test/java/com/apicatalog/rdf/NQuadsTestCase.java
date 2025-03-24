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
package com.apicatalog.rdf;

import jakarta.json.JsonObject;

class NQuadsTestCase {

    public enum Type { POSITIVE, NEGATIVE }

    private final String name;
    private final String comment;
    private final Type type;

    public NQuadsTestCase(final String name, final String comment, final Type type) {
        this.name = name;
        this.comment = comment;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public Type getType() {
        return type;
    }

    public static final NQuadsTestCase of(JsonObject json) {

        Type type = null;

        if ("http://www.w3.org/ns/rdftest#TestNQuadsPositiveSyntax".equals(json.getJsonArray("@type").getString(0))) {

            type = Type.POSITIVE;

        } else if ("http://www.w3.org/ns/rdftest#TestNQuadsNegativeSyntax".equals(json.getJsonArray("@type").getString(0))) {

            type = Type.NEGATIVE;
        }

        final String name = json.getJsonArray("http://www.w3.org/2001/sw/DataAccess/tests/test-manifest#name").getJsonObject(0).getString("@value");

        final String comment = json.getJsonArray("http://www.w3.org/2000/01/rdf-schema#comment").getJsonObject(0).getString("@value");

        return new NQuadsTestCase(name, comment, type);
    }

    @Override
    public String toString() {
        return type.name().toLowerCase() + ": " + comment;
    }
}