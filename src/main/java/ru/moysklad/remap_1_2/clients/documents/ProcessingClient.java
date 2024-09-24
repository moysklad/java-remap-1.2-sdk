package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.Processing;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class ProcessingClient
        extends EntityClientBase
        implements
        GetListEndpoint<Processing>,
        PostEndpoint<Processing>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        MetadataAttributeOperationEndpoint,
        GetByIdEndpoint<Processing>,
        PutByIdEndpoint<Processing>,
        MassCreateUpdateDeleteEndpoint<Processing>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<Processing> {

    public ProcessingClient(ApiClient api) {
        super(api, "/entity/processing/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Processing.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }
}
