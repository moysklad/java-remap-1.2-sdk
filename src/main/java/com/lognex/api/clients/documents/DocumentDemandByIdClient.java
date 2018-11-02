package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.ExportEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.DemandDocumentEntity;

public final class DocumentDemandByIdClient
        extends ApiClient
        implements
        PostEndpoint<DemandDocumentEntity>,
        ExportEndpoint {

    public DocumentDemandByIdClient(LognexApi api, String id) {
        super(api, "/entity/demand/" + id + "/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return DemandDocumentEntity.class;
    }
}
