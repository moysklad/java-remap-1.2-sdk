package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.FactureOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class FactureOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<FactureOut>,
        PostEndpoint<FactureOut>,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        DocumentNewEndpoint<FactureOut>,
        GetByIdEndpoint<FactureOut>,
        PutByIdEndpoint<FactureOut>,
        DeleteByIdEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public FactureOutClient(ApiClient api) {
        super(api, "/entity/factureout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return FactureOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
