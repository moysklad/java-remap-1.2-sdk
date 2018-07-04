package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.RetailDrawerCashInEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentRetailDrawerCashInClient implements
        GetListEndpoint<RetailDrawerCashInEntity>,
        PostEndpoint<RetailDrawerCashInEntity>,
        DeleteByIdEndpoint<RetailDrawerCashInEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/retaildrawercashin/";
    }

    DocumentRetailDrawerCashInClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Внесений
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<RetailDrawerCashInEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, RetailDrawerCashInEntity.class, expand);
    }

    /**
     * Создание нового Внесения
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public RetailDrawerCashInEntity post(RetailDrawerCashInEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, RetailDrawerCashInEntity.class);
    }

    /**
     * Удаление Внесения
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
