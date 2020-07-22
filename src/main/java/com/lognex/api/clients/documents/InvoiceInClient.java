package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DocumentPosition;
import com.lognex.api.entities.documents.InvoiceIn;
import com.lognex.api.entities.documents.positions.InvoiceDocumentPosition;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class InvoiceInClient
        extends EntityClientBase
        implements
        GetListEndpoint<InvoiceIn>,
        PostEndpoint<InvoiceIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<InvoiceIn>,
        PutByIdEndpoint<InvoiceIn>,
        MassCreateUpdateDeleteEndpoint<InvoiceIn>,
        DocumentPositionsEndpoint<InvoiceDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint {

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
