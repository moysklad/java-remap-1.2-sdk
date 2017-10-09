package com.lognex.api.request.builder;

import com.lognex.api.endpoint.ApiClient;
import com.lognex.api.util.Constants;
import lombok.Getter;


public abstract class RequestBuilder {

    @Getter
    protected ApiClient client;

    protected StringBuilder url;

    protected RequestBuilder(ApiClient apiClient){
        this.client = apiClient;
        url = new StringBuilder(Constants.HOST_URL2);
    }
}
