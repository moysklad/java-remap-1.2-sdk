package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.RetailDemandDocumentEntity;

public class DocumentRetailDemandClient
        extends ApiClient
        implements
        GetListEndpoint<RetailDemandDocumentEntity>,
        PostEndpoint<RetailDemandDocumentEntity>,
        DeleteByIdEndpoint {

    public DocumentRetailDemandClient(LognexApi api) {
        super(api, "/entity/retaildemand/", RetailDemandDocumentEntity.class);
    }
}
