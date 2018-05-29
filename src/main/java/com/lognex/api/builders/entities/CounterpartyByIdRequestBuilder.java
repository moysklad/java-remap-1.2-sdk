package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Counterparty;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyByIdRequestBuilder {
    private final LognexApi api;
    private final String id;

    CounterpartyByIdRequestBuilder(LognexApi api, String id) {
        this.api = api;
        this.id = id;
    }

    /**
     * Получение Контрагента с указанным id
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public Counterparty get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/counterparty/" + id).
                get(Counterparty.class);
    }

    /**
     * Удаление Контрагента с указанным id
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void delete() throws IOException, LognexApiException {
        HttpRequestBuilder.
                path(api, "/entity/counterparty/" + id).
                delete();
    }

    /**
     * Обновление данных Контрагента с указанным id
     *
     * @param updatedEntity сущность с новыми данными (<b>Внимание!</b> В этот объект после успешного
     *                      выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void put(Counterparty updatedEntity) throws IOException, LognexApiException {
        Counterparty responseEntity = HttpRequestBuilder.
                path(api, "/entity/counterparty/" + id).
                body(updatedEntity).
                put(Counterparty.class);

        updatedEntity.set(responseEntity);
    }
}
