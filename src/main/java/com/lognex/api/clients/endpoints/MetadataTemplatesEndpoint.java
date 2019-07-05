package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.Meta;
import com.lognex.api.entities.TemplateEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public interface MetadataTemplatesEndpoint extends Endpoint {
    @ApiEndpoint
    default ListEntity<TemplateEntity> embeddedtemplate() throws IOException, LognexApiException {
        ListEntity<TemplateEntity> templates = HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "embeddedtemplate/").
                list(TemplateEntity.class);
        return setEntityType(templates);
    }

    @ApiEndpoint
    default TemplateEntity embeddedtemplate(String id) throws IOException, LognexApiException {
        TemplateEntity template = HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "embeddedtemplate/" + id).
                get(TemplateEntity.class);
        Meta.Type entityType = getEntityType();
        template.setEntityType(entityType);
        return template;
    }

    @ApiEndpoint
    default ListEntity<TemplateEntity> customtemplate() throws IOException, LognexApiException {
        ListEntity<TemplateEntity> templates = HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "customtemplate/").
                list(TemplateEntity.class);
        return setEntityType(templates);
    }

    @ApiEndpoint
    default TemplateEntity customtemplate(String id) throws IOException, LognexApiException {
        TemplateEntity template = HttpRequestExecutor.
                path(api(), path() + (path().endsWith("metadata/") ? "" : "metadata/") + "customtemplate/" + id).
                get(TemplateEntity.class);
        Meta.Type entityType = getEntityType();
        template.setEntityType(entityType);
        return template;
    }

    default ListEntity<TemplateEntity> setEntityType(ListEntity<TemplateEntity> templates) {
        Meta.Type entityType = getEntityType();
        List<TemplateEntity> rows = templates.getRows();
        if (rows != null) {
            templates.setRows(rows.stream().peek(t -> t.setEntityType(entityType)).collect(Collectors.toList()));
        }
        return templates;
    }

    default Meta.Type getEntityType() {
        return Meta.Type.find(path()
                .replace("metadata", "")
                .replace("entity", "")
                .replace("/", "")
        );
    }
}
