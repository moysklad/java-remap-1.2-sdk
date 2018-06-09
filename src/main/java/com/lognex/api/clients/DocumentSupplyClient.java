package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.SupplyDocumentEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentSupplyClient implements GetListEndpoint<SupplyDocumentEntity>, PostEndpoint<SupplyDocumentEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/supply";
    }

    DocumentSupplyClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Приёмок
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListEntity<SupplyDocumentEntity> get() throws IOException, LognexApiException {
        return get(api, SupplyDocumentEntity.class);
    }

    /**
     * Создание новой Приёмки
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(SupplyDocumentEntity newEntity) throws IOException, LognexApiException {
        post(api, newEntity, SupplyDocumentEntity.class);
    }
}
