package com.lognex.api.request.builder.entity;

import com.lognex.api.request.MSReadListRequest;
import com.lognex.api.util.ID;

public interface MetadataRequestBuilder {

    SingleEntityRequestBuilder embeddedTemplate(ID id);

    MSReadListRequest embeddedTemplate();

    SingleEntityRequestBuilder customTemplate(ID id);

    MSReadListRequest customTemplate();
}
