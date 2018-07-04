package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class StoreClient implements GetListEndpoint<StoreEntity>, PostEndpoint<StoreEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/store";
    }

    StoreClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Складов
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<StoreEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, StoreEntity.class, expand);
    }

    /**
     * Создание нового Склада
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public StoreEntity post(StoreEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, StoreEntity.class);
    }
}
