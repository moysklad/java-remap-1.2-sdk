package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CommissionReportOutDocumentEntity;

public final class DocumentCommissionReportOutClient
        extends ApiClient
        implements
        GetListEndpoint<CommissionReportOutDocumentEntity>,
        PostEndpoint<CommissionReportOutDocumentEntity> {

    public DocumentCommissionReportOutClient(LognexApi api) {
        super(api, "/entity/commissionreportout/", CommissionReportOutDocumentEntity.class);
    }
}
