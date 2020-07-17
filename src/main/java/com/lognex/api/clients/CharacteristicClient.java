package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Variant;
import com.lognex.api.responses.CharacteristicListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

public final class CharacteristicClient
        extends EntityClientBase
        implements
        GetByIdEndpoint<Variant.Characteristic>,
        PostEndpoint<Variant.Characteristic>
{

    public CharacteristicClient(ApiClient api) {
        super(api, "/entity/variant/metadata/characteristics/");
    }

    /**
     * Получить список всех характеристик.<br>
     * У полученных объектов поле value всегда null так как это характеристика, а не её значение для модификации товара
     */
    public CharacteristicListEntity get(ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                get(CharacteristicListEntity.class);
    }

    /**
     *  Создание новой характеристики.<br>
     *  При создании характеристики <b>учитывается только поле name</b>.
     */
    @Override
    public Variant.Characteristic create(Variant.Characteristic newEntity) throws IOException, ApiClientException {
        return PostEndpoint.super.create(newEntity);
    }

    /**
     *  Массовое создание новых характеристик.<br>
     *  При создании характеристики <b>учитывается только поле name</b>.
     */
    public List<Variant.Characteristic> create(Collection<Variant.Characteristic> entities) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                body(entities).
                postList(Variant.Characteristic.class);
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Variant.Characteristic.class;
    }
}
