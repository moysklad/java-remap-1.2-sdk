package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.builders.entities.endpoints.GetListEndpoint;
import com.lognex.api.builders.entities.endpoints.PostEndpoint;
import com.lognex.api.entities.Contract;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class ContractRequestBuilder implements GetListEndpoint<Contract>, PostEndpoint<Contract> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/contract";
    }

    ContractRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Договоров
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListResponse<Contract> get() throws IOException, LognexApiException {
        return get(api, Contract.class);
    }

    /**
     * Создание нового Договора
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void post(Contract newEntity) throws IOException, LognexApiException {
        post(api, newEntity, Contract.class);
    }
}
