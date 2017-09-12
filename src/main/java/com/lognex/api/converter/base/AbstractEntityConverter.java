package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.Converter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.ID;
import com.lognex.api.util.StreamUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

public abstract class AbstractEntityConverter<T extends AbstractEntity> implements Converter<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractEntityConverter.class);
    protected static final ObjectMapper om = new ObjectMapper();

    @Override
    public T convert(String response) throws ConverterException {
        try {
            JsonNode root = om.readTree(response.getBytes());
            return convertFromJson(root);
        } catch (IOException e) {
            logger.error("Can't convert to entity", e);
            throw new ConverterException(e);
        }
    }

    @Override
    public List<T> convertToList(String response) throws ConverterException {
        try {
            JsonNode rows = om.readTree(response.getBytes()).get("rows");
            return StreamUtils.stream(rows)
                    .map(this::convertFromJson)
                    .collect(toList());
        } catch (IOException e) {
            logger.error("Can't convert to entity", e);
            throw new ConverterException(e);
        }
    }

    protected void convertToEntity(final AbstractEntity entity, JsonNode node) {
        entity.setId(new ID(node.get("id").textValue()));
        entity.setAccountId(new ID(node.get("accountId").textValue()));
    }

    protected abstract T convertFromJson(JsonNode node) throws ConverterException;
}
