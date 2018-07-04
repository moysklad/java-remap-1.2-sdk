package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.RetailShiftEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class RetailShiftClient implements
        GetListEndpoint<RetailShiftEntity>,
        DeleteByIdEndpoint<RetailShiftEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/retailshift/";
    }

    RetailShiftClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Смен
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<RetailShiftEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, RetailShiftEntity.class, expand);
    }

    /**
     * Удаление Смены
     *
     * @param id идентификатор сущности
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public void delete(String id) throws IOException, LognexApiException {
        delete(api, id);
    }
}
