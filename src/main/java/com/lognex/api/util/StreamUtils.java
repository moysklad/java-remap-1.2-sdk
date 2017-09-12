package com.lognex.api.util;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Spliterator.ORDERED;

public final class StreamUtils {

    private StreamUtils() {} // no instances for you

    public static Stream<JsonNode> stream(JsonNode node) {
        Spliterator<JsonNode> spliterator = Spliterators.spliterator(node.iterator(), node.size(), ORDERED);
        return StreamSupport.stream(spliterator, false);
    }

}
