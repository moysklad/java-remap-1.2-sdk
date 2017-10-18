package com.lognex.api.request.builder.entity;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.request.MSCreateRequest;
import com.lognex.api.request.MSMetadataRequest;
import com.lognex.api.request.MSReadListRequest;
import com.lognex.api.request.MSTemplateRequest;
import com.lognex.api.util.ID;

public interface EntityRequestBuilder {

    SingleEntityRequestBuilder id(ID id);

    MSReadListRequest list();

    MSTemplateRequest template(AbstractEntity entity);

    MSMetadataRequest metadata();

    MSCreateRequest create(AbstractEntity entity);
}
