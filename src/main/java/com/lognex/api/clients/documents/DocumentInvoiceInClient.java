package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.InvoiceInDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

import javax.print.Doc;

public final class DocumentInvoiceInClient
        extends ApiClient
        implements
        GetListEndpoint<InvoiceInDocumentEntity>,
        PostEndpoint<InvoiceInDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<InvoiceInDocumentEntity>,
        PutByIdEndpoint<InvoiceInDocumentEntity>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentInvoiceInClient(LognexApi api) {
        super(api, "/entity/invoicein/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceInDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
