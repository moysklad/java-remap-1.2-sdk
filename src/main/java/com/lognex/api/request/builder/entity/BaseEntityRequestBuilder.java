package com.lognex.api.request.builder.entity;

import com.lognex.api.endpoint.ApiClient;
import com.lognex.api.request.builder.RequestBuilder;

public abstract class BaseEntityRequestBuilder extends RequestBuilder {
    protected BaseEntityRequestBuilder(ApiClient apiClient) {
        super(apiClient);
        url.append("/").append("entity");
    }
}
