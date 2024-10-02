package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.InvoiceIn;
import ru.moysklad.remap_1_2.entities.documents.positions.InvoiceDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class InvoiceInClient
        extends EntityClientBase
        implements
        GetListEndpoint<InvoiceIn>,
        PostEndpoint<InvoiceIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        GetByIdEndpoint<InvoiceIn>,
        PutByIdEndpoint<InvoiceIn>,
        MassCreateUpdateDeleteEndpoint<InvoiceIn>,
        DocumentPositionsEndpoint<InvoiceDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<InvoiceIn> {

    public InvoiceInClient(ApiClient api) {
        super(api, "/entity/invoicein/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<InvoiceDocumentPosition> documentPositionClass() {
        return InvoiceDocumentPosition.class;
    }
}
