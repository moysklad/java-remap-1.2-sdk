package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.responses.metadata.VariantMetadataResponse;

public final class VariantClient
        extends EntityClientBase
        implements
        GetListEndpoint<Variant>,
        PostEndpoint<Variant>,
        DeleteByIdEndpoint,
        MetadataEndpoint<VariantMetadataResponse>,
        GetByIdEndpoint<Variant>,
        PutByIdEndpoint<Variant>,
        MassCreateUpdateDeleteEndpoint<Variant>,
        HasImagesEndpoint<Variant> {

    public VariantClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/variant/");
    }

    public CharacteristicClient characteristics() {
        return new CharacteristicClient(api());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Variant.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return VariantMetadataResponse.class;
    }
}