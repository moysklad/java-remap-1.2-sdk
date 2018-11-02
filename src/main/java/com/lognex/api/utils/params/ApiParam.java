package com.lognex.api.utils.params;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public abstract class ApiParam {
    @Getter
    protected final Type type;

    public ApiParam(Type type) {
        this.type = type;
    }

    public static String renderStringQueryFromList(Type type, List<ApiParam> list) {
        return list.stream().
                map(ApiParam::render).
                collect(Collectors.joining(type.separator));
    }

    protected abstract String render();

    public enum Type {
        filter(";"),
        expand,
        limit,
        offset,
        search,
        order;

        @Getter
        private final String separator;

        Type() {
            this(",");
        }

        Type(String separator) {
            this.separator = separator;
        }
    }
}
