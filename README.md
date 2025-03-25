
# Titanium RDF Primitives

A Java implementation of RDF primitives.

[![Java 8 CI](https://github.com/filip26/titanium-rdf-primitives/actions/workflows/java8-build.yml/badge.svg)](https://github.com/filip26/titanium-rdf-primitives/actions/workflows/java8-build.yml)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7192329820b64135a49073b6187abcd8)](https://app.codacy.com/gh/filip26/titanium-rdf-primitives/dashboard?utm_source=gh&utm_medium=referral&utm_content=&utm_campaign=Badge_grade)
[![Maven Central](https://img.shields.io/maven-central/v/com.apicatalog/titanium-rdf-primitives.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:com.apicatalog%20AND%20a:titanium-rdf-primitives)
[![javadoc](https://javadoc.io/badge2/com.apicatalog/titanium-rdf-primitives/javadoc.svg)](https://javadoc.io/doc/com.apicatalog/titanium-rdf-primitives)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

Formerly part of [Titanium JSON-LD](https://github.com/filip26/titanium-json-ld)

## Examples

```javascript

// Create an empty dataset
var dataset = new OrderedQuadDataset();

// Materialize N-Quads as an RdfDataset
new NQuadsReader(reader).provide(new QuadAcceptor(dataset));

// Get the default graph
Graph graph = dataset.defaultGraph();
```

```javascript
// Initialize the QuadEmitter with an N-Quads writer
var emitter = new QuadEmitter(new NQuadsWriter(writer));

// Serialize the entire dataset as N-Quads
emitter.emit(dataset);

// Serialize a specific named graph as N-Quads
emitter.emit(graph, graphName);

// Serialize a single quad as N-Quads
emitter.emit(quad);
```


## Installation

### Maven

```xml
<dependency>
    <groupId>com.apicatalog</groupId>
    <artifactId>titanium-rdf-primitives</artifactId>
    <version>1.0.2</version>
</dependency>
```

### Gradle

```gradle
implementation("com.apicatalog:titanium-rdf-primitives:1.0.2")
```

## Contributing

All PR's welcome!


### Building

Fork and clone the project repository.

```bash
> cd titanium-rdf-primitives
> mvn package
```

## Libraries

* [Titanium JSON-LD](https://github.com/filip26/titanium-json-ld)
* [Titanium RDF N-QUADS](https://github.com/filip26/titanium-rdf-n-quads)
* [Titanium RDF Dataset Canonicalization](https://github.com/filip26/titanium-rdf-canon)

## Resources

 * [W3C RDF 1.1](https://www.w3.org/TR/rdf11-concepts/)
 * [W3C RDF 1.2 (RDF-star)](https://www.w3.org/TR/rdf12-concepts/)
