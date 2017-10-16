package com.lognex.api.model.entity;


public enum CurrencyRateUpdateType {
    MANUAL("manual"), AUTO("auto");

    private final String apiName;

    CurrencyRateUpdateType(String apiName) {
        this.apiName = apiName;
    }

    public String getApiName() {
        return apiName;
    }
}
