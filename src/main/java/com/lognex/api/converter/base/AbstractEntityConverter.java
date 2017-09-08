package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.util.ID;

public abstract class AbstractEntityConverter {
    protected void convertToEntity(final AbstractEntity entity, JsonNode node) {
        entity.setId(new ID(node.get("id").textValue()));
        entity.setAccountId(new ID(node.get("accountId").textValue()));
    }
}
