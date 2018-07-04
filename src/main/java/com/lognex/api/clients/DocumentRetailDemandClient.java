package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.RetailDemandDocumentEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentRetailDemandClient implements
        GetListEndpoint<RetailDemandDocumentEntity>,
        PostEndpoint<RetailDemandDocumentEntity>,
        DeleteByIdEndpoint<RetailDemandDocumentEntity> {

    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/retaildemand/";
    }

    DocumentRetailDemandClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Розничных Продаж
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<RetailDemandDocumentEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, RetailDemandDocumentEntity.class, expand);
    }

    /**
     * Создание новой Розничной Продажи
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public RetailDemandDocumentEntity post(RetailDemandDocumentEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, RetailDemandDocumentEntity.class);
    }

    /**
     * Удаление Розничной Продажи
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
