package com.lognex.api.request.filter;

public class NumberFilter extends Filter<Number> {

    public NumberFilter(String fieldName, FilterOperator operator, Number value) {
        super(fieldName, operator, value);
    }

    @Override
    public String toFilterString() {
        return toFilterString(null);
    }

    @Override
    public String toFilterString(String host) {
        return fieldName + operator.getSign() + value;
    }
}
