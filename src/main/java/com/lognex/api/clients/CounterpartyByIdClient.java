package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.DeleteEndpoint;
import com.lognex.api.clients.endpoints.GetEndpoint;
import com.lognex.api.clients.endpoints.PutEndpoint;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyByIdClient implements GetEndpoint<CounterpartyEntity>, DeleteEndpoint<CounterpartyEntity>, PutEndpoint<CounterpartyEntity> {
    private final LognexApi api;
    private final String id;

    @Override
    public String path() {
        return "/entity/counterparty/" + id;
    }

    CounterpartyByIdClient(LognexApi api, String id) {
        this.api = api;
        this.id = id;
    }

    /**
     * Получение Контрагента с указанным id
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public CounterpartyEntity get(String... expand) throws IOException, LognexApiException {
        return get(api, CounterpartyEntity.class, expand);
    }

    /**
     * Удаление Контрагента с указанным id
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public void delete() throws IOException, LognexApiException {
        delete(api);
    }

    /**
     * Обновление данных Контрагента с указанным id
     *
     * @param updatedEntity сущность с новыми данными (<b>Внимание!</b> В этот объект после успешного
     *                      выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public void put(CounterpartyEntity updatedEntity) throws IOException, LognexApiException {
        put(api, updatedEntity, CounterpartyEntity.class);
    }
}
