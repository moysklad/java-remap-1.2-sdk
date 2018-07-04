package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.GetMetadataEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.responses.CounterpartyMetadataListResponse;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyClient implements
        GetListEndpoint<CounterpartyEntity>,
        PostEndpoint<CounterpartyEntity>,
        GetMetadataEndpoint<CounterpartyMetadataListResponse> {

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
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<CounterpartyEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, CounterpartyEntity.class, expand);
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
    @Override
    public CounterpartyMetadataListResponse metadata() throws IOException, LognexApiException {
        return metadata(api, CounterpartyMetadataListResponse.class);
    }
}
