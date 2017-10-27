package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.model.entity.Template;
import com.lognex.api.util.Type;

import java.util.Optional;

import static org.apache.http.util.TextUtils.isEmpty;

public abstract class TemplateConverter<T extends Template> extends EntityConverter<T> {

    @Override
    protected void convertToEntity(T entity, JsonNode node) {
        super.convertToEntity(entity, node);

        entity.setName(ConverterUtil.getString(node, "name"));
        entity.setType(ConverterUtil.getString(node, "type"));
        entity.setContent(ConverterUtil.getString(node, "content"));
        getEntityType(node).ifPresent(type -> entity.setEntityType(type));
    }

    private Optional<Type> getEntityType(JsonNode node) {
        String metaHref = ConverterUtil.getMetaHref(node);
        if (isEmpty(metaHref)) {
            return Optional.empty();
        }
        String[] hrefChunks = metaHref.split("/");
        if (hrefChunks.length < 10) {
            return Optional.empty();
        }
        return Optional.of(Type.find(hrefChunks[7]));
    }

}
