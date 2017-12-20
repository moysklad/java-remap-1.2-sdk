package com.lognex.api.request;

import com.lognex.api.ApiClient;
import org.apache.http.client.methods.HttpUriRequest;

public class MSRequestTest extends MSRequest {

    private MSRequest baseRequest;

    public MSRequestTest(MSRequest baseRequest){
        this(baseRequest.getUrl(), baseRequest.getClient());
        this.baseRequest = baseRequest;
        this.options = baseRequest.getOptions();
    }

    private MSRequestTest(String url, ApiClient client) {
        super(url, client);
    }

    @Override
    protected HttpUriRequest buildRequest() {
        HttpUriRequest request = baseRequest.buildRequest();
        options.forEach(o -> request.addHeader(o.getHeader(), o.getValue()));
        return request;
    }

    public HttpUriRequest getHttpRequestInternal() {
        return buildRequest();
    }

}
