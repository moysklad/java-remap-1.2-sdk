package com.lognex.api.request.filter;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.model.entity.attribute.AttributeValue;
import com.lognex.api.util.Constants;
import com.lognex.api.util.DateUtils;
import com.lognex.api.util.MetaHrefUtils;
import com.lognex.api.util.Type;

import java.util.Date;

public class AttributeFilter extends Filter<AttributeValue> {

    public AttributeFilter(Type entityType, Attribute attribute, FilterOperator operator, AttributeValue value) {
        super(prepareFieldName(entityType, attribute), operator, value);
    }

    private static String prepareFieldName(Type entityType, Attribute attribute){
        return Constants.DEFAULT_HOST_URL + "/" + Constants.ENTITY_PATH + "/"
                + entityType.name().toLowerCase() + "/" + Constants.METADATA_PATH + "/attributes/" + attribute.getId();
    }


    @Override
    public String toFilterString() {
        return fieldName + operator.getSign() + prepareValue();
    }

    private String prepareValue(){
        if (value.getValue() instanceof String
                || value.getValue() instanceof Number
                || value.getValue() instanceof Boolean){
            return String.valueOf(value.getValue());
        } else if (value.getValue() instanceof Date){
            return DateUtils.format((Date) value.getValue());
        } else if (value.getValue() instanceof AbstractEntity){
            AbstractEntity abstractEntity = (AbstractEntity) value.getValue();
            Type t = Type.find(abstractEntity.getClass());
            return MetaHrefUtils.makeHref(t, abstractEntity);
        } else {
            throw new IllegalStateException("Unknown type of attribute value=" + value.getValue().getClass());
        }
    }

}
