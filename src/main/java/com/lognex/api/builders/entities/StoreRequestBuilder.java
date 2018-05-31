package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.builders.entities.endpoints.GetListEndpoint;
import com.lognex.api.builders.entities.endpoints.PostEndpoint;
import com.lognex.api.entities.Store;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class StoreRequestBuilder implements GetListEndpoint<Store>, PostEndpoint<Store> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/store";
    }

    StoreRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Складов
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListResponse<Store> get() throws IOException, LognexApiException {
        return get(api, Store.class);
    }

    /**
     * Создание нового Склада
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(Store newEntity) throws IOException, LognexApiException {
        post(api, newEntity, Store.class);
    }
}
