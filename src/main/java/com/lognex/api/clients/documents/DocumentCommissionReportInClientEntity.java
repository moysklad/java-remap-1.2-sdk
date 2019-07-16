package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CommissionReportIn;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class DocumentCommissionReportInClientEntity
        extends EntityApiClient
        implements
        GetListEndpoint<CommissionReportIn>,
        PostEndpoint<CommissionReportIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<CommissionReportIn>,
        PutByIdEndpoint<CommissionReportIn>,
        DocumentPositionsEndpoint,
        ExportEndpoint {

    public DocumentCommissionReportInClientEntity(ApiClient api) {
        super(api, "/entity/commissionreportin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CommissionReportIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
