package com.lognex.api.request;

import com.lognex.api.ApiClient;
import org.apache.http.client.methods.HttpUriRequest;

public class MSUpdateRequest extends MSRequest {

    public MSUpdateRequest(String url, ApiClient client) {
        super(url, client);
    }

    @Override
    protected HttpUriRequest buildRequest() {
        return null;
    }
}
