package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.model.base.AbstractEntityWithOwner;
import com.lognex.api.model.entity.Employee;
import com.lognex.api.model.entity.Group;
import com.lognex.api.util.MetaHrefParser;

public abstract class AbstractEntityWithOwnerConverter<T extends AbstractEntityWithOwner> extends AbstractEntityConverter<T> {
    protected void convertToEntity(final AbstractEntityWithOwner entity, JsonNode node) {
        super.convertToEntity(entity, node);
        /*TODO добавить поддержку expand*/
        entity.setOwner(node.get("owner") == null ? null : new Employee(MetaHrefParser.getId(node.get("owner").get("meta").get("href").asText())));
        entity.setGroup(node.get("group") == null ? null : new Group(MetaHrefParser.getId(node.get("group").get("meta").get("href").asText())));
        entity.setShared(node.get("shared") == null ? false : node.get("shared").asBoolean());
    }
}
