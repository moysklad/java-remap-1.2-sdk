package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.RetailShift;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class RetailShiftClient
        extends EntityClientBase
        implements
        GetListEndpoint<RetailShift>,
        DeleteByIdEndpoint,
        MetadataAttributeOperationEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        ExportEndpoint,
        PublicationEndpoint,
        HasFilesEndpoint<RetailShift> {

    public RetailShiftClient(ApiClient api) {
        super(api, "/entity/retailshift/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return RetailShift.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }
}
