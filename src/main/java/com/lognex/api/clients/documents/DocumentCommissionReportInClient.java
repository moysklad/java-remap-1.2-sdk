package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CommissionReportInDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentCommissionReportInClient
        extends ApiClient
        implements
        GetListEndpoint<CommissionReportInDocumentEntity>,
        PostEndpoint<CommissionReportInDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<CommissionReportInDocumentEntity>,
        PutByIdEndpoint<CommissionReportInDocumentEntity>,
        DocumentPositionsEndpoint {

    public DocumentCommissionReportInClient(LognexApi api) {
        super(api, "/entity/commissionreportin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CommissionReportInDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
