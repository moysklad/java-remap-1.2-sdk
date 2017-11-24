package com.lognex.api.request;

import com.google.common.net.UrlEscapers;
import com.lognex.api.ApiClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

public class MSReadSingleRequest extends MSRequest {

    public MSReadSingleRequest(String url, ApiClient client) {
        super(url, client);
    }

    @Override
    protected HttpUriRequest buildRequest() {
        StringBuilder urldBuilder = new StringBuilder(getUrl());
        if (hasParameters()) {
            urldBuilder.append("?");
        }
        addExpandParameter(urldBuilder);
        String uri = urldBuilder.toString();
        String escapedUri = UrlEscapers.urlFragmentEscaper().escape(uri);
        return new HttpGet(escapedUri);
    }
}
