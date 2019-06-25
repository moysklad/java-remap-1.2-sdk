package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.TemplateEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public interface MetadataTemplatesEndpoint extends Endpoint {
    @ApiEndpoint
    default ListEntity<TemplateEntity> embeddedtemplate() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "embeddedtemplate/").
                list(TemplateEntity.class);
    }

    @ApiEndpoint
    default TemplateEntity embeddedtemplate(String id) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "embeddedtemplate/" + id).
                get(TemplateEntity.class);
    }

    @ApiEndpoint
    default ListEntity<TemplateEntity> customtemplate() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "customtemplate/").
                list(TemplateEntity.class);
    }

    @ApiEndpoint
    default TemplateEntity customtemplate(String id) throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "customtemplate/" + id).
                get(TemplateEntity.class);
    }
}
