package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CustomerOrderDocumentEntity;
import com.lognex.api.responses.DocumentMetadataStatesListResponse;

public class DocumentCustomerOrderClient
        extends ApiClient
        implements
        GetListEndpoint<CustomerOrderDocumentEntity>,
        PostEndpoint<CustomerOrderDocumentEntity>,
        GetMetadataEndpoint<DocumentMetadataStatesListResponse> {

    public DocumentCustomerOrderClient(LognexApi api) {
        super(api, "/entity/customerorder/", CustomerOrderDocumentEntity.class, DocumentMetadataStatesListResponse.class);
    }
}
