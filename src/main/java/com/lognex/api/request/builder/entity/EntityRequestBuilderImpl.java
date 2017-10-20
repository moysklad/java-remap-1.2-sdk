package com.lognex.api.request.builder.entity;

import com.google.common.collect.ImmutableList;
import com.lognex.api.ApiClient;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.request.MSCreateRequest;
import com.lognex.api.request.MSMetadataRequest;
import com.lognex.api.request.MSReadListRequest;
import com.lognex.api.request.MSTemplateRequest;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;


public class EntityRequestBuilderImpl extends BaseEntityRequestBuilder implements EntityRequestBuilder {

    private Type type;
    private final String baseUrl;

    public EntityRequestBuilderImpl(String baseUrl, Type type, ApiClient client){
        super(client, baseUrl);
        this.type = type;
        this.baseUrl = baseUrl;
        url.append("/").append(type.getApiName());
    }

    @Override
    public SingleEntityRequestBuilder id(ID id) {
        return new SingleEntityRequestBuilderImpl(client, baseUrl, type, id);
    }

    @Override
    public SingleEntityRequestBuilder syncId(ID syncId) {
        return new SingleEntityRequestBuilderImpl(client, baseUrl, type, syncId, true);
    }

    @Override
    public MSReadListRequest list() {
        return new MSReadListRequest(url.toString(), client, type);
    }

    @Override
    public MSTemplateRequest template(AbstractEntity entity) {
        return new MSTemplateRequest(url.append("/").append("new").toString(), client, entity);
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
