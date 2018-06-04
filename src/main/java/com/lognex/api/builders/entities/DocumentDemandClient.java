package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.builders.entities.endpoints.GetListEndpoint;
import com.lognex.api.builders.entities.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.Demand;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentDemandClient implements GetListEndpoint<Demand>, PostEndpoint<Demand> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/demand";
    }

    DocumentDemandClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Отгрузок
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListResponse<Demand> get() throws IOException, LognexApiException {
        return get(api, Demand.class);
    }

    /**
     * Создание новой Отгрузки
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(Demand newEntity) throws IOException, LognexApiException {
        post(api, newEntity, Demand.class);
    }
}
