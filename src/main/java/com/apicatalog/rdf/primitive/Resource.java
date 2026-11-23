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

public record Resource(String id, boolean isBlankNode) implements RdfResource {

    public Resource {
        id = Objects.requireNonNull(id);

        if (id.isBlank()) {
            throw new IllegalArgumentException(
                    "Resource id must be non empty string, but was id=" + id + ".");
        }
    }

    @Override
    public String toString() {
        return id;
    }
}
