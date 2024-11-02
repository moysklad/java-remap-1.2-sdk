package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.RetailSalesReturn;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class RetailSalesReturnClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailSalesReturn>,
        PostEndpoint<RetailSalesReturn>,
        MassCreateUpdateDeleteEndpoint<RetailSalesReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<RetailSalesReturn> {

    public RetailSalesReturnClient(ApiClient api) {
        super(api, "/entity/retailsalesreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailSalesReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
