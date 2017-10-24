package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.model.base.field.Meta;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.Constants;
import com.lognex.api.util.ID;
import com.lognex.api.util.StreamUtils;
import com.lognex.api.util.Type;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.stream.Collectors.toList;
import static org.apache.http.util.TextUtils.isEmpty;

@Slf4j
public abstract class AbstractEntityConverter<T extends AbstractEntity> implements Converter<T> {
    private static final ObjectMapper om = new ObjectMapper();

    protected String host;

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

    protected void convertToEntity(final T entity, JsonNode node) {
        ID id = ConverterUtil.getId(node, "id");
        entity.setId(id == null ? ConverterUtil.getIdFromMetaHref(node) : id);
        entity.setAccountId(ConverterUtil.getId(node, "accountId"));
    }

    protected abstract T convertFromJson(JsonNode node) throws ConverterException;

    @Override
    public void toJson(CustomJsonGenerator jgen, T entity) throws IOException {
        toJson(jgen, entity, Constants.DEFAULT_HOST_URL);
    }

    @Override
    public void toJson(CustomJsonGenerator jgen, T entity, String host) throws IOException {
        this.host = isEmpty(host) ? Constants.DEFAULT_HOST_URL : host;
        jgen.writeStartObject();
        if (entity.getId() != null) {
            writeMeta(jgen, entity, host);
        }
        convertFields(jgen, entity);
        jgen.writeEndObject();
    }

    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        checkNotNull(entity);
        if (entity.getId() != null) {
            jgen.writeStringFieldIfNotEmpty("id", entity.getId().getValue());
        }
        if (entity.getAccountId() != null) {
            jgen.writeStringFieldIfNotEmpty("accountId", entity.getAccountId().getValue());
        }
    }

    private void writeMeta(CustomJsonGenerator jgen, T entity, String host) throws IOException {
        Type type = Type.find(entity.getClass());
        jgen.writeObjectField("meta", new Meta<>(type, entity, host));
    }

    void convertMetaField(CustomJsonGenerator jgen, String name, AbstractEntity fieldValue) throws IOException {
        if (fieldValue != null) {
            jgen.writeObjectFieldStart(name);
            Type type = Type.find(fieldValue.getClass());
            jgen.writeObjectField("meta", new Meta<>(type, fieldValue, host));
            jgen.writeEndObject();
        }
    }

}
