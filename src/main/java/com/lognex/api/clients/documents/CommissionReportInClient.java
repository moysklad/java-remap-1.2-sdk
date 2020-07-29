package com.lognex.api.clients.documents;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.CommissionReportIn;
import com.lognex.api.entities.documents.positions.CommissionReportDocumentPosition;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CommissionReportInClient
        extends EntityClientBase
        implements
        GetListEndpoint<CommissionReportIn>,
        PostEndpoint<CommissionReportIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<CommissionReportIn>,
        PutByIdEndpoint<CommissionReportIn>,
        MassCreateUpdateDeleteEndpoint<CommissionReportIn>,
        DocumentPositionsEndpoint<CommissionReportDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public CommissionReportInClient(ApiClient api) {
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

    @Override
    public Class<CommissionReportDocumentPosition> documentPositionClass() {
        return CommissionReportDocumentPosition.class;
    }
}
