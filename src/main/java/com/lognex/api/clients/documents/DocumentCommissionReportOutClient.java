package com.lognex.api.clients.documents;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CommissionReportOutDocumentEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentCommissionReportOutClient
        extends ApiClient
        implements
        GetListEndpoint<CommissionReportOutDocumentEntity>,
        PostEndpoint<CommissionReportOutDocumentEntity>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<CommissionReportOutDocumentEntity>,
        PutByIdEndpoint<CommissionReportOutDocumentEntity>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentCommissionReportOutClient(LognexApi api) {
        super(api, "/entity/commissionreportout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CommissionReportOutDocumentEntity.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}