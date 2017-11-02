package com.lognex.api.request.builder.entity;

import com.lognex.api.model.base.Entity;
import com.lognex.api.model.content.ExportTemplate;
import com.lognex.api.model.content.ExportTemplateSet;
import com.lognex.api.request.*;

public interface SingleEntityRequestBuilder {

    MSReadSingleRequest read();

    <T extends Entity>MSUpdateRequest update(T entity);

    EmbeddedCollectionRequestBuilder embeddedCollectionField();

    MSEntityAuditRequest audit();

    MSDeleteRequest delete();

    MSExportRequest export(ExportTemplate template);

    MSExportRequest export(ExportTemplateSet templateSet);
}
