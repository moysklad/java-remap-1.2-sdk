package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.converter.base.EntityLegendableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.State;

import java.io.IOException;

public class StateConverter extends EntityLegendableConverter<State> {

    @Override
    protected State convertFromJson(JsonNode node) throws ConverterException {
        State state = new State();
        super.convertToEntity(state, node);
        state.setColor(ConverterUtil.getInt(node, "color"));
        return state;
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, State entity) throws IOException {
        super.convertFields(jgen, entity);
        jgen.writeIntegerFieldIfNotNull("color", entity.getColor());
    }
}
