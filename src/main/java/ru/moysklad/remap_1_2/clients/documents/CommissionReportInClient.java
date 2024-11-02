package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CommissionReportIn;
import ru.moysklad.remap_1_2.entities.documents.positions.CommissionReportDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CommissionReportInClient
        extends EntityClientBase
        implements
        GetListEndpoint<CommissionReportIn>,
        PostEndpoint<CommissionReportIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        GetByIdEndpoint<CommissionReportIn>,
        PutByIdEndpoint<CommissionReportIn>,
        MassCreateUpdateDeleteEndpoint<CommissionReportIn>,
        DocumentPositionsEndpoint<CommissionReportDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<CommissionReportIn> {

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
