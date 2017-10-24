package com.lognex.api.request.filter;

import com.lognex.api.util.DateUtils;

import java.util.Date;

public class DateFilter extends Filter<Date> {
    public DateFilter(String fieldName, FilterOperator operator, Date value) {
        super(fieldName, operator, value);
    }

    @Override
    public String toFilterString() {
        return toFilterString(null);
    }

    @Override
    public String toFilterString(String host) {
        return fieldName + operator.getSign() + DateUtils.format(value);
    }
}
