package com.lognex.api.request.builder.entity;

import com.lognex.api.endpoint.ApiClient;
import com.lognex.api.request.MSDeleteRequest;
import com.lognex.api.request.MSEntityAuditRequest;
import com.lognex.api.request.MSReadSingleRequest;
import com.lognex.api.request.MSUpdateRequest;
import com.lognex.api.util.ID;

public class SingleEntityRequestBuilderImpl extends BaseEntityRequestBuilder implements SingleEntityRequestBuilder {

    private String type;
    private ID id;

    SingleEntityRequestBuilderImpl(ApiClient client, String type, ID id){
        super(client);
        this.type = type;
        this.id = id;
        url.append("/").append(type).append("/").append(id.getValue());
    }

    @Override
    public MSReadSingleRequest read() {
        return new MSReadSingleRequest(url.toString(), client);
    }

    @Override
    public MSUpdateRequest update() {
        return new MSUpdateRequest(url.toString(), client);
    }

    @Override
    public EmbeddedCollectionRequestBuilder embeddedCollectionField() {
        return null;
    }

    @Override
    public MSEntityAuditRequest audit() {

        return null;
    }

    @Override
    public MSDeleteRequest delete() {
        return new MSDeleteRequest(url.toString(), client);
    }
}
