package com.lognex.api.request.filter;

import com.lognex.api.model.base.Entity;
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

    private static String prepareFieldName(Type entityType, Attribute attribute) {
        return Constants.DEFAULT_HOST_URL + "/" + Constants.ENTITY_PATH + "/"
                + entityType.getApiName() + "/" + Constants.METADATA_PATH + "/attributes/" + attribute.getId();
    }


    @Override
    public String toFilterString() {
        return toFilterString(Constants.DEFAULT_HOST_URL);
    }

    @Override
    public String toFilterString(String host) {
        return fieldName + operator.getSign() + prepareValue(host);
    }

    private String prepareValue(String host) {
        if (value.getValue() instanceof String
                || value.getValue() instanceof Number
                || value.getValue() instanceof Boolean) {
            return String.valueOf(value.getValue());
        } else if (value.getValue() instanceof Date) {
            return DateUtils.format((Date) value.getValue());
        } else if (value.getValue() instanceof Entity) {
            Entity entity = (Entity) value.getValue();
            Type t = Type.find(entity.getClass());
            return MetaHrefUtils.makeHref(t, entity, host);
        } else {
            throw new IllegalStateException("Unknown type of attribute value=" + value.getValue().getClass());
        }
    }

}
