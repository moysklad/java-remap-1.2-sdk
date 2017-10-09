package com.lognex.api.request;

import com.lognex.api.endpoint.ApiClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.Optional;

public class MSReadListRequest extends MSRequest {

    private Optional<Integer> limit = Optional.empty();
    private Optional<Integer> offset = Optional.empty();

    public MSReadListRequest(String url, ApiClient client) {
        super(url, client);
    }

    public MSReadListRequest limit(int limit){
        this.limit = Optional.of(limit);
        return this;
    }

    public MSReadListRequest offset(int offset){
        this.offset = Optional.of(offset);
        return this;
    }

    @Override
    protected HttpUriRequest buildRequest() {
        StringBuilder urldBuilder = new StringBuilder(getUrl());
        if (limit.isPresent() || offset.isPresent()){
            urldBuilder.append("?");
        }
        limit.ifPresent(integer -> appendParam(urldBuilder, "limit", integer));
        offset.ifPresent(integer -> appendParam(urldBuilder, "offset", integer));
        addExpandParameter(urldBuilder);
        return new HttpGet(getUrl());
    }
}
