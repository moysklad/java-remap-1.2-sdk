package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class EmployeeClient implements GetListEndpoint<EmployeeEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/employee";
    }

    EmployeeClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Скидок
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<EmployeeEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, EmployeeEntity.class, expand);
    }
}
