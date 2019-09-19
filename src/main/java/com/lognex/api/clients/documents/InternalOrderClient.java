package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.InternalOrder;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class InternalOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<InternalOrder>,
        PostEndpoint<InternalOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<InternalOrder>,
        GetByIdEndpoint<InternalOrder>,
        PutByIdEndpoint<InternalOrder>,
        DocumentPositionsEndpoint,
        ExportEndpoint,
        PublicationEndpoint {

    public InternalOrderClient(ApiClient api) {
        super(api, "/entity/internalorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return InternalOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
