package com.lognex.api.converter.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.ShipmentOutPosition;

public class ShipmentOutPositionConverter extends PositionConverter<ShipmentOutPosition> {
    public ShipmentOutPositionConverter() {
        super(ShipmentOutPosition.class);
    }

    @Override
    protected ShipmentOutPosition convertFromJson(JsonNode node) throws ConverterException {
        ShipmentOutPosition position = new ShipmentOutPosition();
        convertToEntity(position, node);
        return position;
    }
}
