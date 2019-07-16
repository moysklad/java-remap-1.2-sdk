package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.InvoiceIn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentInvoiceInClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<InvoiceIn>,
        PostEndpoint<InvoiceIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<InvoiceIn>,
        PutByIdEndpoint<InvoiceIn>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentInvoiceInClientEntity(ApiClient api) {
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
}
