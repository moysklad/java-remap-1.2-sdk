package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.model.base.AbstractEntityWithOwner;
import com.lognex.api.model.entity.Employee;
import com.lognex.api.model.entity.Group;
import com.lognex.api.util.MetaHrefParser;

public abstract class AbstractEntityWithOwnerConverter extends AbstractEntityConverter {
    protected void convertToEntity(final AbstractEntityWithOwner entity, JsonNode node) {
        super.convertToEntity(entity, node);
        /*TODO добавить поддержку expand*/
        entity.setOwner(new Employee(MetaHrefParser.getId(node.get("owner").get("meta").get("href").asText())));
        entity.setGroup(new Group(MetaHrefParser.getId(node.get("group").get("meta").get("href").asText())));
        entity.setShared(node.get("shared").asBoolean());
    }
}
