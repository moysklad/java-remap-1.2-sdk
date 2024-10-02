package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.PaymentOut;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;

public final class PaymentOutClient
        extends EntityClientBase
        implements
        GetListEndpoint<PaymentOut>,
        PostEndpoint<PaymentOut>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        DocumentNewEndpoint<PaymentOut>,
        GetByIdEndpoint<PaymentOut>,
        PutByIdEndpoint<PaymentOut>,
        MassCreateUpdateDeleteEndpoint<PaymentOut>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<PaymentOut> {

    public PaymentOutClient(ApiClient api) {
        super(api, "/entity/paymentout/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return PaymentOut.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;

    }
}
