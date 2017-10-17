package com.lognex.api.request.filter;

public class StringFilter extends Filter<String> {

    public StringFilter(String fieldName, FilterOperator operator, String value) {
        super(fieldName, operator, value);
    }

    @Override
    public String toFilterString() {
        return fieldName + operator.getSign() + value;
    }
}
