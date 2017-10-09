package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.ID;
import com.lognex.api.util.StreamUtils;
import com.lognex.api.util.Type;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Slf4j
public abstract class AbstractEntityConverter<T extends AbstractEntity> implements Converter<T> {
    protected static final ObjectMapper om = new ObjectMapper();

    @Override
    public T convert(String json) throws ConverterException {
        try {
            JsonNode root = om.readTree(json.getBytes());
            return convertFromJson(root);
        } catch (IOException e) {
            log.error("Can't convert to entity", e);
            throw new ConverterException(e);
        }
    }

    @Override
    public List<T> convertToList(String json) throws ConverterException {
        try {
            JsonNode rows = om.readTree(json.getBytes()).get("rows");
            return StreamUtils.stream(rows)
                    .map(this::convertFromJson)
                    .collect(toList());
        } catch (IOException e) {
            log.error("Can't convert to entity", e);
            throw new ConverterException(e);
        }
    }

    protected void convertToEntity(final AbstractEntity entity, JsonNode node) {
        entity.setId(new ID(node.get("id").textValue()));
        entity.setAccountId(new ID(node.get("accountId").textValue()));
    }

    protected abstract T convertFromJson(JsonNode node) throws ConverterException;

    @Override
    public void toJson(CustomJsonGenerator jgen, T entity) throws IOException {
        jgen.writeStartObject();
        writeMeta(jgen, entity);
        convertFields(jgen, entity);
        jgen.writeEndObject();
    }

    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        jgen.writeStringFieldIfNotEmpty("id", entity.getId().getValue());
        jgen.writeStringFieldIfNotEmpty("accountId", entity.getAccountId().getValue());
    }

    private void writeMeta(CustomJsonGenerator jgen, T entity) throws IOException {
        Type type = Type.find(entity.getClass());
        jgen.writeObjectField("meta", type);
    }


}
