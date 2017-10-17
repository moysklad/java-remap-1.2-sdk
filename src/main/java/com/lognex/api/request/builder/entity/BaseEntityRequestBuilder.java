package com.lognex.api.request.builder.entity;

import com.lognex.api.ApiClient;
import com.lognex.api.request.builder.RequestBuilder;

abstract class BaseEntityRequestBuilder extends RequestBuilder {
    BaseEntityRequestBuilder(ApiClient apiClient, String baseUrl) {
        super(apiClient, baseUrl);
        url.append("/").append("entity");
    }
}
