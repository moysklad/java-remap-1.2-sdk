package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CustomerOrder;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentCustomerOrderClient implements GetListEndpoint<CustomerOrder>, PostEndpoint<CustomerOrder> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/customerorder/";
    }

    DocumentCustomerOrderClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Заказов Покупателей
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<CustomerOrder> get() throws IOException, LognexApiException {
        return get(api, CustomerOrder.class);
    }

    /**
     * Создание нового Заказа Покупателя
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public CustomerOrder post(CustomerOrder newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, CustomerOrder.class);
    }
}
