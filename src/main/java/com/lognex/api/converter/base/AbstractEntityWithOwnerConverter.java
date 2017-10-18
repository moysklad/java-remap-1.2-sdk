package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.model.base.AbstractEntityWithOwner;
import com.lognex.api.model.entity.Employee;
import com.lognex.api.model.entity.Group;
import com.lognex.api.util.MetaHrefUtils;

import java.io.IOException;

public abstract class AbstractEntityWithOwnerConverter<T extends AbstractEntityWithOwner> extends AbstractEntityConverter<T> {
    @Override
    protected void convertToEntity(final T entity, JsonNode node) {
        super.convertToEntity(entity, node);
        /*TODO добавить поддержку expand*/
        entity.setOwner(node.get("owner") == null ? null : new Employee(MetaHrefUtils.getId(node.get("owner").get("meta").get("href").asText())));
        entity.setGroup(node.get("group") == null ? null : new Group(MetaHrefUtils.getId(node.get("group").get("meta").get("href").asText())));
        entity.setShared(ConverterUtil.getBoolean(node, "shared"));
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        super.convertFields(jgen, entity);
        if (entity.getOwner() != null){
            convertMetaField(jgen, "owner", entity.getOwner());
        }
        if (entity.getGroup() != null){
            convertMetaField(jgen, "group", entity.getGroup());
        }
        jgen.writeBooleanField("shared", entity.isShared());
    }
}
