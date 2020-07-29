package com.lognex.api.clients;

import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.BonusTransaction;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedResponse;

public final class BonusTransactionClient
        extends EntityClientBase
        implements
        GetListEndpoint<BonusTransaction>,
        PostEndpoint<BonusTransaction>,
        MetadataEndpoint<MetadataAttributeSharedResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<BonusTransaction>,
        PutByIdEndpoint<BonusTransaction>,
        MassCreateUpdateDeleteEndpoint<BonusTransaction>,
        DeleteByIdEndpoint {

    public BonusTransactionClient(com.lognex.api.ApiClient api) {
        super(api, "/entity/bonustransaction/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return BonusTransaction.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedResponse.class;
    }
}
