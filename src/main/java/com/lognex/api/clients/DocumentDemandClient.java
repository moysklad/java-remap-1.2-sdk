package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.DemandDocumentEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentDemandClient implements GetListEndpoint<DemandDocumentEntity>, PostEndpoint<DemandDocumentEntity> {
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
    public ListEntity<DemandDocumentEntity> get() throws IOException, LognexApiException {
        return get(api, DemandDocumentEntity.class);
    }

    /**
     * Создание новой Отгрузки
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(DemandDocumentEntity newEntity) throws IOException, LognexApiException {
        post(api, newEntity, DemandDocumentEntity.class);
    }
}
