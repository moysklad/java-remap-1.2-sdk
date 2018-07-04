package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.documents.CustomerOrderDocumentEntity;
import com.lognex.api.responses.DocumentMetadataStatesListResponse;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public class DocumentCustomerOrderClient implements
        GetListEndpoint<CustomerOrderDocumentEntity>,
        PostEndpoint<CustomerOrderDocumentEntity>,
        GetMetadataEndpoint<DocumentMetadataStatesListResponse> {

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
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<CustomerOrderDocumentEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, CustomerOrderDocumentEntity.class, expand);
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
    public CustomerOrderDocumentEntity post(CustomerOrderDocumentEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, CustomerOrderDocumentEntity.class);
    }

    /**
     * Получение списка Метаданных Заказов Покупателей
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public DocumentMetadataStatesListResponse metadata() throws IOException, LognexApiException {
        return metadata(api, DocumentMetadataStatesListResponse.class);
    }
}
