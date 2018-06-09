package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.UomEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class UomClient implements GetListEndpoint<UomEntity>, PostEndpoint<UomEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/uom/";
    }

    UomClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Единиц Измерения
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListEntity<UomEntity> get() throws IOException, LognexApiException {
        return get(api, UomEntity.class);
    }

    /**
     * Создание новой Единицы Измерения
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(UomEntity newEntity) throws IOException, LognexApiException {
        post(api, newEntity, UomEntity.class);
    }
}
