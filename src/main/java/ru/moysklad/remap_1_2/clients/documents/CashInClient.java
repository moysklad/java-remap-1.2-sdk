package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CashIn;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CashInClient
        extends EntityClientBase
        implements
        GetListEndpoint<CashIn>,
        PostEndpoint<CashIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<CashIn>,
        GetByIdEndpoint<CashIn>,
        PutByIdEndpoint<CashIn>,
        MassCreateUpdateDeleteEndpoint<CashIn>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<CashIn> {

    public CashInClient(ApiClient api) {
        super(api, "/entity/cashin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CashIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
