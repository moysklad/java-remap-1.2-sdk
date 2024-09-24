package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.RetailDrawerCashOut;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class RetailDrawerCashOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailDrawerCashOut>,
        PostEndpoint<RetailDrawerCashOut>,
        MassCreateUpdateDeleteEndpoint<RetailDrawerCashOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        MetadataAttributeOperationEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<RetailDrawerCashOut> {

    public RetailDrawerCashOutClient(ApiClient api) {
        super(api, "/entity/retaildrawercashout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }
}