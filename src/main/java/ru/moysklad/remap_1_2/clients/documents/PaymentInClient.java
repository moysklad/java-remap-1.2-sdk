package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.PaymentIn;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PaymentInClient
        extends EntityClientBase
        implements
        GetListEndpoint<PaymentIn>,
        PostEndpoint<PaymentIn>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<PaymentIn>,
        GetByIdEndpoint<PaymentIn>,
        PutByIdEndpoint<PaymentIn>,
        MassCreateUpdateDeleteEndpoint<PaymentIn>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<PaymentIn> {

    public PaymentInClient(ApiClient api) {
        super(api, "/entity/paymentin/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentIn.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
