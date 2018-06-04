package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.builders.entities.endpoints.GetListEndpoint;
import com.lognex.api.builders.entities.endpoints.PostEndpoint;
import com.lognex.api.entities.Counterparty;
import com.lognex.api.responses.CounterpartyMetadataListResponse;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyClient implements GetListEndpoint<Counterparty>, PostEndpoint<Counterparty> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/counterparty";
    }

    CounterpartyClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Контрагентов
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListResponse<Counterparty> get() throws IOException, LognexApiException {
        return get(api, Counterparty.class);
    }

    /**
     * Создание нового Контрагента
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(Counterparty newEntity) throws IOException, LognexApiException {
        post(api, newEntity, Counterparty.class);
    }

    /**
     * Получение списка Метаданных Контрагентов
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public CounterpartyMetadataListResponse metadata() throws IOException, LognexApiException {
        return HttpRequestExecutor.
                path(api, "/entity/counterparty/metadata").
                get(CounterpartyMetadataListResponse.class);
    }
}
