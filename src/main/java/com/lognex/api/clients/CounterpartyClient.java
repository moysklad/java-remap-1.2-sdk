package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.responses.CounterpartyMetadataListResponse;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyClient implements GetListEndpoint<CounterpartyEntity>, PostEndpoint<CounterpartyEntity> {
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
    @Override
    public ListEntity<CounterpartyEntity> get() throws IOException, LognexApiException {
        return get(api, CounterpartyEntity.class);
    }

    /**
     * Создание нового Контрагента
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public CounterpartyEntity post(CounterpartyEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, CounterpartyEntity.class);
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
