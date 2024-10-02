package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.InvoiceOut;
import ru.moysklad.remap_1_2.entities.documents.positions.InvoiceDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class InvoiceOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<InvoiceOut>,
        PostEndpoint<InvoiceOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        GetByIdEndpoint<InvoiceOut>,
        PutByIdEndpoint<InvoiceOut>,
        MassCreateUpdateDeleteEndpoint<InvoiceOut>,
        DocumentPositionsEndpoint<InvoiceDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<InvoiceOut> {

    public InvoiceOutClient(ApiClient api) {
        super(api, "/entity/invoiceout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceOut.class;
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
