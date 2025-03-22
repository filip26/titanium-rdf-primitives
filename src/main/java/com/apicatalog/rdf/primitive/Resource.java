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

public class Resource implements RdfResource {

    final String value;
    final boolean blankNode;

    Resource(final String value, boolean isBlankNode) {
        this.value = value;
        this.blankNode = isBlankNode;
    }

    public static Resource createBlankNode(String value) {
        return new Resource(value, true);
    }

    public static Resource createIRI(String value) {
        return new Resource(value, false);
    }

    @Override
    public boolean isBlankNode() {
        return blankNode;
    }

    @Override
    public boolean isIRI() {
        return !blankNode;
    }

    @Override
    public String value() {
        return value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, blankNode);
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
            Resource other = (Resource) obj;
            return Objects.equals(value, other.value)
                    && blankNode == other.blankNode;
        }
        if (!(obj instanceof RdfResource)) {
            return false;
        }
        RdfResource other = (RdfResource) obj;
        return Objects.equals(value, other.value())
                && blankNode == other.isBlankNode();
    }

    @Override
    public String toString() {
        if (blankNode) {
            return Objects.toString(value);
        }
        return '<' + Objects.toString(value) + '>';
    }
}
