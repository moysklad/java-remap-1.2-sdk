package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.products.BundleEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class BundleClient implements GetListEndpoint<BundleEntity>, PostEndpoint<BundleEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/bundle/";
    }

    BundleClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Комплектов
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListEntity<BundleEntity> get() throws IOException, LognexApiException {
        return get(api, BundleEntity.class);
    }

    /**
     * Создание нового Комплекта
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(BundleEntity newEntity) throws IOException, LognexApiException {
        post(api, newEntity, BundleEntity.class);
    }
}
