package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteByIdEndpoint;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.RetailSalesReturnEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentRetailSalesReturnClient implements
        GetListEndpoint<RetailSalesReturnEntity>,
        PostEndpoint<RetailSalesReturnEntity>,
        DeleteByIdEndpoint<RetailSalesReturnEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/retailsalesreturn/";
    }

    DocumentRetailSalesReturnClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Розничных Возвратов
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<RetailSalesReturnEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, RetailSalesReturnEntity.class, expand);
    }

    /**
     * Создание нового Розничного Возврата
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public RetailSalesReturnEntity post(RetailSalesReturnEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, RetailSalesReturnEntity.class);
    }

    /**
     * Удаление Розничного Возврата
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
