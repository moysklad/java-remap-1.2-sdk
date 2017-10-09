package com.lognex.api.request.builder.entity;

import com.lognex.api.request.MSDeleteRequest;
import com.lognex.api.request.MSEntityAuditRequest;
import com.lognex.api.request.MSReadSingleRequest;
import com.lognex.api.request.MSUpdateRequest;

public interface SingleEntityRequestBuilder {

    MSReadSingleRequest read();

    MSUpdateRequest update();

    EmbeddedCollectionRequestBuilder embeddedCollectionField();

    MSEntityAuditRequest audit();

    MSDeleteRequest delete();
}
