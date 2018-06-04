package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.builders.entities.endpoints.DeleteEndpoint;
import com.lognex.api.builders.entities.endpoints.GetEndpoint;
import com.lognex.api.builders.entities.endpoints.PutEndpoint;
import com.lognex.api.entities.Counterparty;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class CounterpartyByIdClient implements GetEndpoint<Counterparty>, DeleteEndpoint<Counterparty>, PutEndpoint<Counterparty> {
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
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public Counterparty get() throws IOException, LognexApiException {
        return get(api, Counterparty.class);
    }

    /**
     * Удаление Контрагента с указанным id
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
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
    public void put(Counterparty updatedEntity) throws IOException, LognexApiException {
        put(api, updatedEntity, Counterparty.class);
    }
}
