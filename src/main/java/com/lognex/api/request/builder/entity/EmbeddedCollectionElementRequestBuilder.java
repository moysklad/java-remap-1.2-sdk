package com.lognex.api.request.builder.entity;

import com.lognex.api.request.MSDeleteRequest;
import com.lognex.api.request.MSReadSingleRequest;
import com.lognex.api.request.MSUpdateRequest;

public interface EmbeddedCollectionElementRequestBuilder {

    MSReadSingleRequest read();

    MSUpdateRequest update();

    MSDeleteRequest delete();
}
