package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public interface CustomFieldsConverter<T> {

    void fromJson(JsonNode node, T entity);

    void toJson(CustomJsonGenerator jgen, T entity) throws IOException;
}
