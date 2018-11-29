package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DemandDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentDemandClient
        extends ApiClient
        implements
        GetListEndpoint<DemandDocumentEntity>,
        PostEndpoint<DemandDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<DemandDocumentEntity>,
        GetByIdEndpoint<DemandDocumentEntity>,
        PutByIdEndpoint<DemandDocumentEntity>,
        DocumentPositionsEndpoint {

    public DocumentDemandClient(LognexApi api) {
        super(api, "/entity/demand/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return DemandDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
