package com.lognex.api.request.builder.entity;

import com.lognex.api.model.base.Entity;
import com.lognex.api.request.MSCreateRequest;
import com.lognex.api.request.MSMetadataRequest;
import com.lognex.api.request.MSReadListRequest;
import com.lognex.api.request.MSTemplateRequest;
import com.lognex.api.util.ID;

public interface EntityRequestBuilder {

    SingleEntityRequestBuilder id(ID id);

    SingleEntityRequestBuilder syncId(ID syncId);

    MSReadListRequest list();

    MSTemplateRequest template(Entity entity);

    MSMetadataRequest metadata();

    MSCreateRequest create(Entity entity);
}
