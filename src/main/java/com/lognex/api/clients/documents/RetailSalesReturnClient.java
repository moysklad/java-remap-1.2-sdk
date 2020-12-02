package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.RetailSalesReturn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class RetailSalesReturnClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailSalesReturn>,
        PostEndpoint<RetailSalesReturn>,
        MassCreateUpdateDeleteEndpoint<RetailSalesReturn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public RetailSalesReturnClient(ApiClient api) {
        super(api, "/entity/retailsalesreturn/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailSalesReturn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
