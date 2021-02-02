package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.ProcessingPlan;

public final class ProcessingPlanClient
        extends EntityClientBase
        implements
        GetListEndpoint<ProcessingPlan>,
        PostEndpoint<ProcessingPlan>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<ProcessingPlan>,
        PutByIdEndpoint<ProcessingPlan>,
        MassCreateUpdateDeleteEndpoint<ProcessingPlan>,
        ExportEndpoint {

    public ProcessingPlanClient(ApiClient api) {
        super(api, "/entity/processingplan/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingPlan.class;
    }
}
