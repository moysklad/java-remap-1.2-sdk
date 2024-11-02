package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.FactureOut;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class FactureOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<FactureOut>,
        PostEndpoint<FactureOut>,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<FactureOut>,
        GetByIdEndpoint<FactureOut>,
        PutByIdEndpoint<FactureOut>,
        MassCreateUpdateDeleteEndpoint<FactureOut>,
        DeleteByIdEndpoint,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<FactureOut> {

    public FactureOutClient(ApiClient api) {
        super(api, "/entity/factureout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return FactureOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
