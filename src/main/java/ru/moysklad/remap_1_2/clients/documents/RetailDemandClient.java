package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.RetailDemand;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class RetailDemandClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailDemand>,
        PostEndpoint<RetailDemand>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        MetadataAttributeOperationEndpoint,
        GetByIdEndpoint<RetailDemand>,
        PutByIdEndpoint<RetailDemand>,
        MassCreateUpdateDeleteEndpoint<RetailDemand>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<RetailDemand> {

    public RetailDemandClient(ApiClient api) {
        super(api, "/entity/retaildemand/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDemand.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }
}
