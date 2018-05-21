package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Counterparty;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyByIdRequestBuilder {
    private final LognexApi api;
    private final String id;

    public CounterpartyByIdRequestBuilder(LognexApi api, String id) {
        this.api = api;
        this.id = id;
    }

    /**
     * Возвращает Контрагента с указанным id
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public Counterparty get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/counterparty/" + id).
                get(Counterparty.class);
    }

    /**
     * Удаляет Контрагента с указанным id
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public void delete() throws IOException, LognexApiException {
        HttpRequestBuilder.
                path(api, "/entity/counterparty/" + id).
                delete();
    }

    /**
     * Удаляет Контрагента с указанным id
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public void put(Counterparty updatedCounterparty) throws IOException, LognexApiException {
        HttpRequestBuilder.
                path(api, "/entity/counterparty/" + id).
                body(updatedCounterparty).
                put();
    }
}
