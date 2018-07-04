package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.RetailStoreEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class RetailStoreClient implements GetListEndpoint<RetailStoreEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/retailstore";
    }

    RetailStoreClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Точек Продаж
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<RetailStoreEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, RetailStoreEntity.class, expand);
    }
}
