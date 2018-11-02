package com.lognex.api.utils.params;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ExpandParam extends ApiParam {
    private final String[] fields;

    private ExpandParam(String[] fields) {
        super(Type.expand);
        this.fields = fields;
    }

    public static ExpandParam expand(String... fields) {
        if (fields == null || fields.length == 0) return null;
        return new ExpandParam(fields);
    }

    @Override
    protected String render() {
        return Arrays.stream(fields).collect(Collectors.joining(type.getSeparator()));
    }
}
