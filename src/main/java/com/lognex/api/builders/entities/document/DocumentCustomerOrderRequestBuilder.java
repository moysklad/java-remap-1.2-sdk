package com.lognex.api.builders.entities.document;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.documents.CustomerOrder;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentCustomerOrderRequestBuilder {
    private final LognexApi api;

    public DocumentCustomerOrderRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Заказов Покупателей
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public ListResponse<CustomerOrder> get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/customerorder").
                getListResponse(CustomerOrder.class);
    }

    /**
     * Создать новый Заказ Покупателя
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public void post(CustomerOrder newEntity) throws IOException, LognexApiException {
        CustomerOrder responseEntity = HttpRequestBuilder.
                path(api, "/entity/customerorder").
                body(newEntity).
                post(CustomerOrder.class);

        newEntity.set(responseEntity);
    }
}
