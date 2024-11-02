package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CommissionReportOut;
import ru.moysklad.remap_1_2.entities.documents.positions.CommissionReportDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CommissionReportOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<CommissionReportOut>,
        PostEndpoint<CommissionReportOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        GetByIdEndpoint<CommissionReportOut>,
        PutByIdEndpoint<CommissionReportOut>,
        MassCreateUpdateDeleteEndpoint<CommissionReportOut>,
        DocumentPositionsEndpoint<CommissionReportDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<CommissionReportOut> {

    public CommissionReportOutClient(ApiClient api) {
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

    @Override
    public Class<CommissionReportDocumentPosition> documentPositionClass() {
        return CommissionReportDocumentPosition.class;
    }
}
