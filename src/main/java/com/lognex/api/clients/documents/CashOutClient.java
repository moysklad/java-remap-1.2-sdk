package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CashOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CashOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<CashOut>,
        PostEndpoint<CashOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<CashOut>,
        GetByIdEndpoint<CashOut>,
        PutByIdEndpoint<CashOut>,
        MassCreateUpdateDeleteEndpoint<CashOut>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public CashOutClient(ApiClient api) {
        super(api, "/entity/cashout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CashOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
