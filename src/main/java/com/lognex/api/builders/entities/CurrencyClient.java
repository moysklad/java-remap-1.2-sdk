package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.builders.entities.endpoints.GetListEndpoint;
import com.lognex.api.builders.entities.endpoints.PostEndpoint;
import com.lognex.api.entities.Currency;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CurrencyClient implements GetListEndpoint<Currency>, PostEndpoint<Currency> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/currency";
    }

    CurrencyClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Валют
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListResponse<Currency> get() throws IOException, LognexApiException {
        return get(api, Currency.class);
    }

    /**
     * Создание новой Валюты
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public void post(Currency newEntity) throws IOException, LognexApiException {
        post(api, newEntity, Currency.class);
    }
}
