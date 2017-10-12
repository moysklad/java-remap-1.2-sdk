package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtill;
import com.lognex.api.converter.base.AbstractEntityLegendableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.ProductFolder;
import com.lognex.api.util.ID;

public class ProductFolderConverter extends AbstractEntityLegendableConverter<ProductFolder> {

    @Override
    protected void convertToEntity(ProductFolder entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);

        entity.setPathName(ConverterUtill.getString(node, "pathName"));
        entity.setArchived(ConverterUtill.getBoolean(node, "archived"));
        entity.setVat(ConverterUtill.getLong(node, "vat"));
        entity.setEffectiveVat(ConverterUtill.getLong(node, "effectiveVat"));
        ID parentGroupId = ConverterUtill.getIdFromMetaHref(node);
        if (parentGroupId != null) {
            entity.setProductFolder(new ProductFolder(parentGroupId));
        }
    }

    @Override
    protected ProductFolder convertFromJson(JsonNode node) throws ConverterException {
        ProductFolder entity = new ProductFolder();
        convertToEntity(entity, node);
        return entity;
    }

}
