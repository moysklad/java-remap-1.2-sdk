package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Counterparty;
import com.lognex.api.responses.CounterpartyMetadataListResponse;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyRequestBuilder {
    private final LognexApi api;

    public CounterpartyRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Контрагентов
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public ListResponse<Counterparty> get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/counterparty").
                getListResponse(Counterparty.class);
    }

    /**
     * Создание нового Контрагента
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public void post(Counterparty newEntity) throws IOException, LognexApiException {
        Counterparty responseEntity = HttpRequestBuilder.
                path(api, "/entity/counterparty").
                body(newEntity).
                post(Counterparty.class);

        newEntity.set(responseEntity);
    }

    /**
     * Возвращает метаданные Контрагентов
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public CounterpartyMetadataListResponse metadata() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/counterparty/metadata").
                get(CounterpartyMetadataListResponse.class);
    }
}
