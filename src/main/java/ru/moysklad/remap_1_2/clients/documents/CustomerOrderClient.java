package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CustomerOrder;
import ru.moysklad.remap_1_2.entities.documents.positions.CustomerOrderDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeOperationSharedStatesResponse;

public final class CustomerOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<CustomerOrder>,
        PostEndpoint<CustomerOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeOperationSharedStatesResponse>,
        GetByIdEndpoint<CustomerOrder>,
        PutByIdEndpoint<CustomerOrder>,
        MassCreateUpdateDeleteEndpoint<CustomerOrder>,
        DocumentPositionsEndpoint<CustomerOrderDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        MetadataAttributeOperationEndpoint,
        HasFilesEndpoint<CustomerOrder> {

    public CustomerOrderClient(ApiClient api) {
        super(api, "/entity/customerorder/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return CustomerOrder.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeOperationSharedStatesResponse.class;
    }

    @Override
    public Class<CustomerOrderDocumentPosition> documentPositionClass() {
        return CustomerOrderDocumentPosition.class;
    }
}
