package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.DocumentMetadataEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.MetadataAttributeEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.InvoiceOutDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentInvoiceOutClient
        extends ApiClient
        implements
        GetListEndpoint<InvoiceOutDocumentEntity>,
        PostEndpoint<InvoiceOutDocumentEntity>,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint {

    public DocumentInvoiceOutClient(LognexApi api) {
        super(api, "/entity/invoiceout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InvoiceOutDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
