package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.Attribute;
import ru.moysklad.remap_1_2.entities.Consignment;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeResponse;

public final class ConsignmentClient
        extends EntityClientBase
        implements
        GetListEndpoint<Consignment>,
        PostEndpoint<Consignment>,
        DeleteByIdEndpoint,
        MetadataEndpoint<MetadataAttributeResponse<Attribute>>,
        MetadataAttributeEndpoint,
        GetByIdEndpoint<Consignment>,
        PutByIdEndpoint<Consignment>,
        MassCreateUpdateDeleteEndpoint<Consignment> {

    public ConsignmentClient(ApiClient api) {
        super(api, "/entity/consignment/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Consignment.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeResponse.class;
    }
}
