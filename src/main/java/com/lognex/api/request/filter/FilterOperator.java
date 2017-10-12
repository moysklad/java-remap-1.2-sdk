package com.lognex.api.request.filter;

import lombok.Getter;

public enum  FilterOperator {
    EQUALS("="), NOT_EQUALS("!=");

    @Getter
    private String sign;

    FilterOperator(String sign){
        this.sign = sign;
    }
}
