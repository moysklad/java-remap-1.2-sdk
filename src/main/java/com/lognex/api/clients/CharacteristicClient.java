package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.GetByIdEndpoint;
import com.lognex.api.entities.products.Variant;
import com.lognex.api.responses.CharacteristicListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public final class CharacteristicClient
        extends EntityClientBase
        implements
        GetByIdEndpoint<Variant.Characteristic> {

    public CharacteristicClient(ApiClient api) {
        super(api, "/entity/variant/metadata/characteristics/");
    }

    public CharacteristicListEntity get(ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                apiParams(params).
                get(CharacteristicListEntity.class);
    }

    public Variant.Characteristic create(String name) throws IOException, ApiClientException {
        Variant.Characteristic characteristic = new Variant.Characteristic();
        characteristic.setName(name);
        return HttpRequestExecutor.
                path(api(), path()).
                body(characteristic).
                post(Variant.Characteristic.class);
    }

    public List<Variant.Characteristic> create(Collection<String> names) throws IOException, ApiClientException {
        List<Variant.Characteristic> items = names.stream()
                .map(Variant.Characteristic::new)
                .collect(Collectors.toList());
        return HttpRequestExecutor.
                path(api(), path()).
                body(items).
                postList(Variant.Characteristic.class);
    }
}
