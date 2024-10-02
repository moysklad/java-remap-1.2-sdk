package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.RetailDrawerCashIn;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class RetailDrawerCashInClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailDrawerCashIn>,
        PostEndpoint<RetailDrawerCashIn>,
        MassCreateUpdateDeleteEndpoint<RetailDrawerCashIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<RetailDrawerCashIn> {

    public RetailDrawerCashInClient(ApiClient api) {
        super(api, "/entity/retaildrawercashin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
