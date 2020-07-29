package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingOrder;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class ProcessingOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<ProcessingOrder>,
        PostEndpoint<ProcessingOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<ProcessingOrder>,
        GetByIdEndpoint<ProcessingOrder>,
        PutByIdEndpoint<ProcessingOrder>,
        MassCreateUpdateDeleteEndpoint<ProcessingOrder>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public ProcessingOrderClient(ApiClient api) {
        super(api, "/entity/processingorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
