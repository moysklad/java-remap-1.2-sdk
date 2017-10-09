package com.lognex.api.request.builder.entity;

import com.google.common.collect.ImmutableList;
import com.lognex.api.ApiClient;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.request.MSCreateRequest;
import com.lognex.api.request.MSMetadataRequest;
import com.lognex.api.request.MSReadListRequest;
import com.lognex.api.util.ID;


public class EntityRequestBuilderImpl extends BaseEntityRequestBuilder implements EntityRequestBuilder {

    private String type;

    public EntityRequestBuilderImpl(String type, ApiClient client){
        super(client);
        this.type = type;
        url.append("/").append(type);
    }

    @Override
    public SingleEntityRequestBuilder id(ID id) {
        return new SingleEntityRequestBuilderImpl(client, type, id);
    }

    @Override
    public MSReadListRequest list() {
        return new MSReadListRequest(url.toString(), client);
    }

    @Override
    public MSMetadataRequest metadata() {
        return new MSMetadataRequest(url.toString(), client);
    }

    @Override
    public MSCreateRequest create(AbstractEntity entity) {
        return new MSCreateRequest(url.toString(), client, ImmutableList.of(entity));
    }

}
