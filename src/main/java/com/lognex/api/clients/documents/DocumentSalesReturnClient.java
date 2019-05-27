package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.SalesReturnDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentSalesReturnClient
        extends ApiClient
        implements
        GetListEndpoint<SalesReturnDocumentEntity>,
        PostEndpoint<SalesReturnDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<SalesReturnDocumentEntity>,
        GetByIdEndpoint<SalesReturnDocumentEntity>,
        PutByIdEndpoint<SalesReturnDocumentEntity>,
        DocumentPositionsEndpoint {

    public DocumentSalesReturnClient(LognexApi api) {
        super(api, "/entity/salesreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SalesReturnDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
