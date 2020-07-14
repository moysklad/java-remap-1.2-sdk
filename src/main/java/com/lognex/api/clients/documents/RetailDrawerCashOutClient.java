package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailDrawerCashOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class RetailDrawerCashOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailDrawerCashOut>,
        PostEndpoint<RetailDrawerCashOut>,
        MassCreateUpdateDeleteEndpoint<RetailDrawerCashOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public RetailDrawerCashOutClient(ApiClient api) {
        super(api, "/entity/retaildrawercashout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailDrawerCashOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}