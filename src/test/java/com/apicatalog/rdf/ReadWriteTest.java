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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.TreeSet;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.rdf.NQuadsTestCase.Type;
import com.apicatalog.rdf.model.RdfQuad;
import com.apicatalog.rdf.nquads.NQuadsReader;
import com.apicatalog.rdf.nquads.NQuadsReaderException;
import com.apicatalog.rdf.nquads.NQuadsWriter;
import com.apicatalog.rdf.primitive.Dataset;
import com.apicatalog.rdf.primitive.Quad;
import com.apicatalog.rdf.provider.DatasetProvider;
import com.apicatalog.rdf.provider.QuadEmitter;
import com.apicatalog.rdf.provider.QuadSetProvider;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonValue.ValueType;
import jakarta.json.stream.JsonParser;

class ReadWriteTest {

    private final static String TEST_CASE_BASE_PATH = "nquads-test-suite/";

    @ParameterizedTest(name = "{0}")
    @MethodSource("data")
    void testOrderedQuadSet(NQuadsTestCase testCase) throws IOException, URISyntaxException {

        assertNotNull(testCase);
        assertNotNull(testCase.getName());
        assertNotNull(testCase.getType());

        try (final InputStream is = ReadWriteTest.class
                .getResourceAsStream(TEST_CASE_BASE_PATH + testCase.getName() + ".nq")) {

            final String input = isToString(is);
            assertNotNull(input);

            var quads = new LinkedHashSet<RdfQuad>();

            try (var reader = new NQuadsReader(new StringReader(input))) {
                reader.read(new QuadSetProvider(quads));
            }

            final StringWriter writer = new StringWriter();

            QuadEmitter.emit(new NQuadsWriter(writer), quads);

            final String result = writer.toString();
            assertNotNull(result);

            assertEquals(Type.POSITIVE, testCase.getType());

            String expected = input;

            try (final InputStream out = ReadWriteTest.class
                    .getResourceAsStream(TEST_CASE_BASE_PATH + testCase.getName() + ".out.nq")) {
                if (out != null) {
                    expected = isToString(out);
                }
            }

            final boolean match = expected.equals(result);

            if (!match) {
                System.out.println(testCase.getName());
                System.out.println("Expected:");
                System.out.println(expected);
                System.out.println("Result:");
                System.out.println(result);
            }

            assertTrue(match);

        } catch (IllegalArgumentException | NQuadsReaderException e) {
            assertEquals(Type.NEGATIVE, testCase.getType());
        }
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("data")
    void testOrderedQuadDataset(NQuadsTestCase testCase) throws IOException, URISyntaxException {

        assertNotNull(testCase);
        assertNotNull(testCase.getName());
        assertNotNull(testCase.getType());

        try (final InputStream is = ReadWriteTest.class
                .getResourceAsStream(TEST_CASE_BASE_PATH + testCase.getName() + ".nq")) {

            final String input = isToString(is);
            assertNotNull(input);

            final var dataset = new Dataset();

            try (var reader = new NQuadsReader(new StringReader(input))) {
                reader.read(new DatasetProvider(dataset));
            }

            var quads = new TreeSet<RdfQuad>(Comparator.comparing(quad -> {

                var result = new String[1];

                QuadEmitter.emit((subject, predicate, object, datatype, language, direction, graph) -> {
                    result[0] = NQuadsWriter.nquad(subject, predicate, object, datatype, language, direction, graph);
                }, quad);

                return result[0];
            }));

            assertEquals(Type.POSITIVE, testCase.getType());

            try (var reader = new NQuadsReader(new StringReader(input))) {
                reader.read(new QuadSetProvider(quads));
            }

            int found = 0;

            for (var triple : dataset.defaultGraph()) {
                if (!quads.contains(new Quad(
                        triple.subject(),
                        triple.predicate(),
                        triple.object(),
                        null))) {
                    fail();
                }
                found++;
            }

            for (var graph : dataset.graphNames()) {

                for (var triple : dataset.namedGraph(graph)) {
                    if (!quads.contains(new Quad(
                            triple.subject(),
                            triple.predicate(),
                            triple.object(),
                            graph))) {
                        fail();
                    }
                    found++;
                }
            }

            assertEquals(quads.size(), found);

        } catch (IllegalArgumentException | NQuadsReaderException e) {
            assertEquals(Type.NEGATIVE, testCase.getType());
        }
    }

    static final Stream<NQuadsTestCase> data() throws IOException {
        return load(TEST_CASE_BASE_PATH, "manifest.json");
    }

    static final Stream<NQuadsTestCase> load(String path, String name) throws IOException {
        try (final InputStream is = ReadWriteTest.class.getResourceAsStream(path + name)) {
            final JsonParser parser = Json.createParser(is);

            parser.next();

            return parser
                    .getArray()
                    .stream()
                    .filter(v -> ValueType.OBJECT.equals(v.getValueType()))
                    .map(JsonObject.class::cast)
                    .map(NQuadsTestCase::of)
                    .filter(tc -> Type.POSITIVE.equals(tc.getType()));
        }
    }

    static final String isToString(InputStream is) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        for (int length; (length = is.read(buffer)) != -1;) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
