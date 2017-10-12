package com.lognex.api.request.builder;

import com.lognex.api.ApiClient;
import lombok.Getter;


public abstract class RequestBuilder {

    @Getter
    protected ApiClient client;

    protected StringBuilder url;

    protected RequestBuilder(ApiClient apiClient, String baseUrl){
        this.client = apiClient;
        url = new StringBuilder(baseUrl);
    }
}
