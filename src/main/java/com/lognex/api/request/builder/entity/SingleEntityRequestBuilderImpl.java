package com.lognex.api.request.builder.entity;

import com.lognex.api.ApiClient;
import com.lognex.api.model.base.Entity;
import com.lognex.api.model.content.ExportTemplate;
import com.lognex.api.model.content.ExportTemplateSet;
import com.lognex.api.request.MSDeleteRequest;
import com.lognex.api.request.MSEntityAuditRequest;
import com.lognex.api.request.MSReadSingleRequest;
import com.lognex.api.request.MSUpdateRequest;
import com.lognex.api.request.MSExportRequest;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;

public class SingleEntityRequestBuilderImpl extends BaseEntityRequestBuilder implements SingleEntityRequestBuilder {

    SingleEntityRequestBuilderImpl(ApiClient client, String baseUrl, Type type, ID id){
        this(client, baseUrl, type, id, false);
    }

    SingleEntityRequestBuilderImpl(ApiClient client, String baseUrl, Type type, ID id, boolean isSyncId){
        super(client, baseUrl);
        url.append("/").append(type.getApiName()).append("/");
        if (isSyncId) {
            url.append("syncid").append('/');
        }
        url.append(id.getValue());
    }

    SingleEntityRequestBuilderImpl(ApiClient client, String baseUrl, ID id) {
        super(client, baseUrl);
        url.append('/').append(id.getValue());
    }

    @Override
    public MSReadSingleRequest read() {
        return new MSReadSingleRequest(url.toString(), client);
    }

    @Override
    public <T extends Entity> MSUpdateRequest update(T t) {
        return new MSUpdateRequest<>(url.toString(), client, t);
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

    @Override
    public MSExportRequest export(ExportTemplate template) {
        return new MSExportRequest(getExportUrl(), client, template);
    }

    @Override
    public MSExportRequest export(ExportTemplateSet templateSet) {
        return new MSExportRequest(getExportUrl(), client, templateSet);
    }

    private String getExportUrl() {
        url.append('/').append("export").append('/');
        return url.toString();
    }
}
