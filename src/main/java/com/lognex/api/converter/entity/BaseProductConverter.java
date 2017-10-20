package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.AbstractEntityLegendableConverter;
import com.lognex.api.converter.base.CustomJsonGenerator;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.BaseProduct;

import java.io.IOException;

public abstract class BaseProductConverter<T extends BaseProduct> extends AbstractEntityLegendableConverter<T> {

    private PriceConverter priceConverter = new PriceConverter();
    private ProductFolderConverter productFolderConverter = new ProductFolderConverter();

    @Override
    protected void convertToEntity(final T entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);
        entity.setSyncId(ConverterUtil.getId(node, "syncId"));
        productFolderConverter.convertToEntity(entity, node);
        entity.setMinPrice(ConverterUtil.getDouble(node, "minPrice"));
        parseSalePrices(entity, node);
    }

    @Override
    protected void convertFields(CustomJsonGenerator jgen, T entity) throws IOException {
        super.convertFields(jgen, entity);
        if ( entity.getSyncId() != null) {
            jgen.writeStringFieldIfNotEmpty("syncId", entity.getSyncId().getValue());
        }
    }

    private void parseSalePrices(final T entity, JsonNode node) {
        ArrayNode salePrices = ConverterUtil.getArray(node, "salePrices");
        if (salePrices != null) {
            for (JsonNode salePrice : salePrices) {
                entity.getSalePrices().add(priceConverter.convertFromJson(salePrice));
            }
        }
    }

}
