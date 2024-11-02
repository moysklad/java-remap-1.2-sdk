package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.Contract;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class ContractClient
        extends EntityClientBase
        implements
        GetListEndpoint<Contract>,
        PostEndpoint<Contract>,
        MassCreateUpdateDeleteEndpoint<Contract>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Contract>,
        PutByIdEndpoint<Contract>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint {

    public ContractClient(ApiClient api) {
        super(api, "/entity/contract/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Contract.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
