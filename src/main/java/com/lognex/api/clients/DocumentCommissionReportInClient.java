package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CommissionReportInDocumentEntity;

public final class DocumentCommissionReportInClient
        extends ApiClient
        implements
        GetListEndpoint<CommissionReportInDocumentEntity>,
        PostEndpoint<CommissionReportInDocumentEntity> {

    public DocumentCommissionReportInClient(LognexApi api) {
        super(api, "/entity/commissionreportin/", CommissionReportInDocumentEntity.class);
    }
}
