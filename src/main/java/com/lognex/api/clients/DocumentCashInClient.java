package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CashInDocumentEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentCashInClient implements
        GetListEndpoint<CashInDocumentEntity>,
        PostEndpoint<CashInDocumentEntity>,
        DeleteByIdEndpoint<CashInDocumentEntity> {

    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/cashin/";
    }

    DocumentCashInClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Приходных Ордеров
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<CashInDocumentEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, CashInDocumentEntity.class, expand);
    }

    /**
     * Создание новой Приходного Ордера
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public CashInDocumentEntity post(CashInDocumentEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, CashInDocumentEntity.class);
    }

    /**
     * Удаление Приходного Ордера
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
