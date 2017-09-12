package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lognex.api.converter.Converter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.ID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityConverter<T extends AbstractEntity> implements Converter<T> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractEntityConverter.class);

    @Override
    public T convert(String response) throws ConverterException {
        try {
            JsonNode root = new ObjectMapper().readTree(response.getBytes());
            return convertFromJson(root);
        } catch (IOException e) {
            logger.error("Can't convert to entity", e);
            throw new ConverterException(e);
        }
    }

    @Override
    public List<T> convertToList(String response) throws ConverterException {
        int size;
        JsonNode rows;
        try {
            JsonNode root = new ObjectMapper().readTree(response.getBytes());
            size = root.get("meta").get("size").asInt();
            rows = root.get("rows");
        } catch (IOException e) {
            logger.error("Can't convert to entity", e);
            throw new ConverterException(e);
        }

        List<T> result = new ArrayList<>(size);

        for (int i = 0; i < size ; i++) {
            result.add(convertFromJson(rows.get(i)));
        }
        return result;
    }

    protected void convertToEntity(final AbstractEntity entity, JsonNode node) {
        entity.setId(new ID(node.get("id").textValue()));
        entity.setAccountId(new ID(node.get("accountId").textValue()));
    }

    protected abstract T convertFromJson(JsonNode node) throws ConverterException;
}
