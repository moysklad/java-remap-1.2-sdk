package com.lognex.api.request.builder.entity;

import com.lognex.api.request.MSCreateRequest;
import com.lognex.api.request.MSReadListRequest;

public interface EmbeddedCollectionRequestBuilder {
    MSReadListRequest list();

    EmbeddedCollectionElementRequestBuilder id();

    MSCreateRequest create();

}
