package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingPlan;

public final class DocumentProcessingPlanClient
        extends com.lognex.api.clients.ApiClient
        implements
        GetListEndpoint<ProcessingPlan>,
        PostEndpoint<ProcessingPlan>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ProcessingPlan>,
        PutByIdEndpoint<ProcessingPlan>,
        ExportEndpoint {

    public DocumentProcessingPlanClient(ApiClient api) {
        super(api, "/entity/processingplan/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingPlan.class;
    }
}
