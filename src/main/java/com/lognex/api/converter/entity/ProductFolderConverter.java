package com.lognex.api.converter.entity;

import com.fasterxml.jackson.databind.JsonNode;
import com.lognex.api.converter.ConverterUtil;
import com.lognex.api.converter.base.EntityLegendableConverter;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.entity.good.ProductFolder;
import com.lognex.api.util.ID;

public class ProductFolderConverter extends EntityLegendableConverter<ProductFolder> {

    @Override
    protected void convertToEntity(ProductFolder entity, JsonNode node) throws ConverterException {
        super.convertToEntity(entity, node);

        entity.setPathName(ConverterUtil.getString(node, "pathName"));
        entity.setArchived(ConverterUtil.getBoolean(node, "archived"));
        entity.setVat(ConverterUtil.getLong(node, "vat"));
        entity.setEffectiveVat(ConverterUtil.getLong(node, "effectiveVat"));
        ID parentGroupId = ConverterUtil.getIdFromMetaHref(node);
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
