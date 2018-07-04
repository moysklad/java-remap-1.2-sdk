package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.RetailDrawerCashOutEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentRetailDrawerCashOutClient implements
        GetListEndpoint<RetailDrawerCashOutEntity>,
        PostEndpoint<RetailDrawerCashOutEntity>,
        DeleteByIdEndpoint<RetailDrawerCashOutEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/retaildrawercashout/";
    }

    DocumentRetailDrawerCashOutClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Выплат
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<RetailDrawerCashOutEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, RetailDrawerCashOutEntity.class, expand);
    }

    /**
     * Создание новой Выплаты
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public RetailDrawerCashOutEntity post(RetailDrawerCashOutEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, RetailDrawerCashOutEntity.class);
    }

    /**
     * Удаление Выплаты
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
