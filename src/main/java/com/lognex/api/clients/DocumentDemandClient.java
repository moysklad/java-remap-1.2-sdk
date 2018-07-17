package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.DemandDocumentEntity;

public final class DocumentDemandClient
        extends ApiClient
        implements
        GetListEndpoint<DemandDocumentEntity>,
        PostEndpoint<DemandDocumentEntity> {

    public DocumentDemandClient(LognexApi api) {
        super(api, "/entity/demand", DemandDocumentEntity.class);
    }
}
