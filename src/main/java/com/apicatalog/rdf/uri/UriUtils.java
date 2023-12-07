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
package com.apicatalog.rdf.uri;

import java.util.Arrays;

import com.apicatalog.rdf.lang.RdfAlphabet;

public final class UriUtils {

    private UriUtils() {
    }

    public static final boolean isAbsoluteUri(final String uri) {
        // then validate just a scheme
        return startsWithScheme(uri);
    }

    private static final boolean startsWithScheme(final String uri) {

        if (uri == null
                || uri.length() < 2 // a scheme must have at least one letter followed by ':'
                || !Character.isLetter(uri.codePointAt(0)) // a scheme name must start with a letter
                ) {
            return false;
        }

        for (int i = 1; i < uri.length(); i++) {

            if (
                //  a scheme name must start with a letter followed by a letter/digit/+/-/.
                Character.isLetterOrDigit(uri.codePointAt(i))
                        || uri.charAt(i) == '-' || uri.charAt(i) == '+' || uri.charAt(i) == '.'
                ) {
                continue;
            }

            // a scheme name must be terminated by ':'
            return uri.charAt(i) == ':';
        }
        return false;
    }
    
    /**
     * BLANK_NODE_LABEL ::= '_:' (PN_CHARS_U | [0-9]) ((PN_CHARS | '.')* PN_CHARS)?
     *
     * @see <a href="https://www.w3.org/TR/n-quads/#sec-grammar">N-Quads Grammar</a>
     *
     * @param blankNodeId to check
     * @return <code>true</code> if the provided string is well formed blank node identifier
     */
    public static boolean isBlanNode(final String blankNodeId) {

        if (blankNodeId == null) {
            throw new IllegalArgumentException();
        }

        if (blankNodeId.length() < 3) {
            return false;
        }

        int[] chars = blankNodeId.codePoints().toArray();

        if (chars[0] != '_'
                || chars[1] != ':'
                || (RdfAlphabet.PN_CHARS_U.negate().test(chars[2])
                        && RdfAlphabet.ASCII_DIGIT.negate().test(chars[2]))
                || chars[chars.length - 1] == '.'
                        )  {
            return false;
        }

        if (chars.length == 3) {
            return true;
        }

        return Arrays.stream(chars, 3, chars.length - 1).allMatch(RdfAlphabet.PN_CHARS.or(ch -> ch == '.'));
    }

}
