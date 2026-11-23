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
 * Represents an RDF resource, which can either be an absolute URI or a blank
 * node identifier.
 * <p>
 * This interface defines methods for working with RDF resources, which are key
 * components in RDF statements (subject, predicate, or object in RDF triples).
 * An RDF resource can either be an URI or a blank node.
 */
public interface RdfResource extends RdfTerm {

    /**
     * Returns the id of the resource, typically an absolute URI or blank node
     * identifier stripped of <code>"_:"</code> prefix.
     * 
     * @return the id of the resource as a {@link String}, never <code>null</code>
     */
    String id();

    /**
     * Checks whether this resource is a variable, i.e. blank node.
     * <p>
     * A blank node is an anonymous resource that does not have an absolute URI.
     * 
     * @return {@code true} if this resource is a blank node, {@code false} if it is
     *         an URI
     */
    boolean isBlankNode();
}
