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
package com.apicatalog.rdf.io.nquad;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.apicatalog.rdf.Rdf;
import com.apicatalog.rdf.RdfDataset;
import com.apicatalog.rdf.RdfNQuad;
import com.apicatalog.rdf.RdfResource;
import com.apicatalog.rdf.RdfValue;
import com.apicatalog.rdf.io.error.RdfWriterException;
import com.apicatalog.rdf.io.error.UnsupportedContentException;
import com.apicatalog.rdf.io.nquad.writer.NQuadsWriterTestCase;
import com.apicatalog.rdf.io.nquad.writer.NQuadsWriterTestSuite;
import com.apicatalog.rdf.lang.RdfConstants;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonString;
import jakarta.json.JsonValue;
import jakarta.json.JsonValue.ValueType;
import jakarta.json.stream.JsonParser;

class NQuadsWriterTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("data")
    void testWrite(NQuadsWriterTestCase testCase) throws IOException, URISyntaxException, RdfWriterException, UnsupportedContentException {

        assertNotNull(testCase);
        assertNotNull(testCase.getInput());
        assertNotNull(testCase.getExpected());

        String result = null;

        try (final InputStream is = getClass().getResourceAsStream(testCase.getInput())) {

            assertNotNull(is);

            JsonParser parser = Json.createParser(is);
            parser.next();

            JsonArray input = parser.getArray();
            assertNotNull(input);

            final RdfDataset dataset = Rdf.createDataset();

            for (final JsonValue statement : input) {
                dataset.add(createStatement(statement.asJsonObject()));
            }

            final ByteArrayOutputStream os = new ByteArrayOutputStream();

            Rdf.createWriter(RdfConstants.N_QUADS_MEDIA_TYPE, os).write(dataset);

            result = os.toString().trim();
        }

        try (final BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(testCase.getExpected())))) {

            String expected = reader.lines().collect(Collectors.joining("\n")).trim();

            if (!Objects.equals(expected, result)) {
                System.out.println("Expected: ");
                System.out.println(expected);
                System.out.println();
                System.out.println("Result: ");
                System.out.println(result);
            }

            assertEquals(expected, result);
        }
    }

    static final Stream<NQuadsWriterTestCase> data() throws IOException, URISyntaxException {
        return NQuadsWriterTestSuite.load();
    }

    private static final RdfNQuad createStatement(final JsonObject value) {

        RdfResource subject = Rdf.createResource(value.getString("subject"));

        RdfResource predicate = Rdf.createResource(value.getString("predicate"));

        RdfValue object = createObject(value.get("object"));

        RdfResource graph = null;

        if (value.containsKey("graph")) {
            graph = Rdf.createResource(value.getString("graph"));
        }

        return Rdf.createNQuad(subject, predicate, object, graph);
    }

    private static final RdfValue createObject(final JsonValue value) {

        if (value != null && ValueType.STRING == value.getValueType()) {

            final String stringValue = ((JsonString)value).getString();

            return Rdf.createValue(stringValue);
        }

        JsonObject object = value.asJsonObject();

        if (object.containsKey("@type")) {
            return Rdf.createTypedString(object.getString("@value"), object.getString("@type"));
        }

        if (object.containsKey("@language")) {
            return Rdf.createLangString(object.getString("@value"), object.getString("@language"));
        }

        throw new IllegalStateException();
    }
}
