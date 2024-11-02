package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.ProcessingOrder;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class ProcessingOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<ProcessingOrder>,
        PostEndpoint<ProcessingOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<ProcessingOrder>,
        GetByIdEndpoint<ProcessingOrder>,
        PutByIdEndpoint<ProcessingOrder>,
        MassCreateUpdateDeleteEndpoint<ProcessingOrder>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<ProcessingOrder> {

    public ProcessingOrderClient(ApiClient api) {
        super(api, "/entity/processingorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
