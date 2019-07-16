package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CommissionReportOut;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentCommissionReportOutClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<CommissionReportOut>,
        PostEndpoint<CommissionReportOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<CommissionReportOut>,
        PutByIdEndpoint<CommissionReportOut>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentCommissionReportOutClientEntity(ApiClient api) {
        super(api, "/entity/commissionreportout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CommissionReportOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
