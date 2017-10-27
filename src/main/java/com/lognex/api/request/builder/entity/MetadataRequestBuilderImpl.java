package com.lognex.api.request.builder.entity;

import com.lognex.api.ApiClient;
import com.lognex.api.request.MSReadListRequest;
import com.lognex.api.request.builder.RequestBuilder;
import com.lognex.api.util.ID;

public class MetadataRequestBuilderImpl extends RequestBuilder implements MetadataRequestBuilder {
    private static String EMBEDDED_TEMPLATE = "embeddedtemplate";
    private static String CUSTOM_TEMPLATE = "customtemplate";

    MetadataRequestBuilderImpl(ApiClient apiClient, String baseUrl) {
        super(apiClient, baseUrl);
    }

    @Override
    public SingleEntityRequestBuilder embeddedTemplate(ID id) {
        return new SingleEntityRequestBuilderImpl(getClient(), getTemplateUrl(EMBEDDED_TEMPLATE), id);
    }

    @Override
    public MSReadListRequest embeddedTemplate() {
        return new MSReadListRequest(getTemplateUrl(EMBEDDED_TEMPLATE), client);
    }

    @Override
    public SingleEntityRequestBuilder customTemplate(ID id) {
        return new SingleEntityRequestBuilderImpl(getClient(), getTemplateUrl(CUSTOM_TEMPLATE), id);
    }

    @Override
    public MSReadListRequest customTemplate() {
        return new MSReadListRequest(getTemplateUrl(CUSTOM_TEMPLATE), client);
    }

    private String getTemplateUrl(String templateType) {
        return url.append('/').append(templateType).append('/').toString();
    }
}
