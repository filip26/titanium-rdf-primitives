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
package com.apicatalog.rdf.model;

/**
 * Represents an RDF resource, which can either be an absolute IRI or a blank
 * node identifier.
 * <p>
 * This interface defines methods for working with RDF resources, which are key
 * components in RDF statements (subject, predicate, or object in RDF triples).
 * An RDF resource can either be an IRI or a blank node.
 */
public interface RdfResource extends RdfTerm {

    /**
     * Returns the value of the resource, typically an absolute IRI or blank node
     * identifier.
     * 
     * @return the value of the resource as a {@link String}
     */
    String value();

    /**
     * Checks whether this resource is a blank node.
     * <p>
     * A blank node is an anonymous resource that does not have an IRI.
     * 
     * @return {@code true} if this resource is a blank node, {@code false} if it is
     *         an IRI
     */
    boolean isBlank();

    /**
     * Checks whether this resource is an IRI.
     * <p>
     * An IRI (Internationalized Resource Identifier) is a globally unique
     * identifier used to refer to a resource.
     * 
     * @return {@code true} if this resource is an IRI, {@code false} if it is a
     *         blank node
     */
    boolean isIRI();

    @Override
    default boolean isResource() {
        return true;
    }

    @Override
    default RdfResource asResource() {
        return this;
    }
}
