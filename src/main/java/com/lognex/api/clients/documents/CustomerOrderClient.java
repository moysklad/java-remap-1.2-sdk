package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CustomerOrder;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CustomerOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<CustomerOrder>,
        PostEndpoint<CustomerOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        GetByIdEndpoint<CustomerOrder>,
        PutByIdEndpoint<CustomerOrder>,
        DocumentPositionsEndpoint,
        ExportEndpoint,
        PublicationEndpoint {

    public CustomerOrderClient(ApiClient api) {
        super(api, "/entity/customerorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CustomerOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
