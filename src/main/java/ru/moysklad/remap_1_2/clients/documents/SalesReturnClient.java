package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.SalesReturn;
import ru.moysklad.remap_1_2.entities.documents.positions.SalesReturnDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class SalesReturnClient
        extends EntityClientBase
        implements
        GetListEndpoint<SalesReturn>,
        PostEndpoint<SalesReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<SalesReturn>,
        GetByIdEndpoint<SalesReturn>,
        PutByIdEndpoint<SalesReturn>,
        MassCreateUpdateDeleteEndpoint<SalesReturn>,
        DocumentPositionsEndpoint<SalesReturnDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<SalesReturn> {

    public SalesReturnClient(ApiClient api) {
        super(api, "/entity/salesreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SalesReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<SalesReturnDocumentPosition> documentPositionClass() {
        return SalesReturnDocumentPosition.class;
    }
}
