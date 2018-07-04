package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.ContractEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class ContractClient implements GetListEndpoint<ContractEntity>, PostEndpoint<ContractEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/contract";
    }

    ContractClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Договоров
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<ContractEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, ContractEntity.class, expand);
    }

    /**
     * Создание нового Договора
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ContractEntity post(ContractEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, ContractEntity.class);
    }
}
