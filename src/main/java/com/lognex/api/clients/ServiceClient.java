package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.ServiceEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class ServiceClient implements GetListEndpoint<ServiceEntity>, PostEndpoint<ServiceEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/service/";
    }

    ServiceClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Услуг
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListEntity<ServiceEntity> get() throws IOException, LognexApiException {
        return get(api, ServiceEntity.class);
    }

    /**
     * Создание новой Услуги
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(ServiceEntity newEntity) throws IOException, LognexApiException {
        post(api, newEntity, ServiceEntity.class);
    }
}
