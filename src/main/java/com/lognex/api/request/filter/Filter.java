package com.lognex.api.request.filter;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class Filter<T> {
    String fieldName;
    FilterOperator operator;
    protected T value;

    Filter(String fieldName, FilterOperator operator, T value){
        checkNotNull(fieldName);
        this.fieldName = fieldName;
        this.operator = operator;
        this.value = value;
    }

    public abstract String toFilterString();
}
