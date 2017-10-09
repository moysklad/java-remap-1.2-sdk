package com.lognex.api.request;

import com.lognex.api.endpoint.ApiClient;
import org.apache.http.client.methods.HttpUriRequest;

public class MSDeleteRequest extends MSRequest {

    public MSDeleteRequest(String url, ApiClient client) {
        super(url, client);
    }

    @Override
    protected HttpUriRequest buildRequest() {
        return null;
    }
}
