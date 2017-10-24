package com.lognex.api.request.filter;

public class BooleanFilter extends Filter<Boolean>{

    public BooleanFilter(String fieldName, FilterOperator operator, Boolean value) {
        super(fieldName, operator, value);
    }

    @Override
    public String toFilterString() {
        return toFilterString(null);
    }

    @Override
    public String toFilterString(String host) {
        return fieldName + operator.getSign() + String.valueOf(value);
    }
}
