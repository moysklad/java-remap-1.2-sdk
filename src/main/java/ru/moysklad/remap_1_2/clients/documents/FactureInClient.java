package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.FactureIn;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class FactureInClient
        extends EntityClientBase
        implements
        GetListEndpoint<FactureIn>,
        PostEndpoint<FactureIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<FactureIn>,
        GetByIdEndpoint<FactureIn>,
        PutByIdEndpoint<FactureIn>,
        MassCreateUpdateDeleteEndpoint<FactureIn>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<FactureIn> {

    public FactureInClient(ApiClient api) {
        super(api, "/entity/facturein/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return FactureIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
