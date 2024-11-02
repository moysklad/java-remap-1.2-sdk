package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.CustomerOrder;
import ru.moysklad.remap_1_2.entities.documents.positions.CustomerOrderDocumentPosition;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class CustomerOrderClient
        extends EntityClientBase
        implements
        GetListEndpoint<CustomerOrder>,
        PostEndpoint<CustomerOrder>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        GetByIdEndpoint<CustomerOrder>,
        PutByIdEndpoint<CustomerOrder>,
        MassCreateUpdateDeleteEndpoint<CustomerOrder>,
        DocumentPositionsEndpoint<CustomerOrderDocumentPosition>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        MetadataDocumentAttributeEndpoint,
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
        return MetadataAttributeSharedStatesResponse.class;
    }

    @Override
    public Class<CustomerOrderDocumentPosition> documentPositionClass() {
        return CustomerOrderDocumentPosition.class;
    }
}
