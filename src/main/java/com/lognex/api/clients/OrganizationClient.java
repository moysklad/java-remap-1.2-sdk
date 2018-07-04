package com.lognex.api.clients;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.endpoints.GetListEndpoint;
import com.lognex.api.clients.endpoints.PostEndpoint;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class OrganizationClient implements GetListEndpoint<OrganizationEntity>, PostEndpoint<OrganizationEntity> {
    private final LognexApi api;

    @Override
    public String path() {
        return "/entity/organization";
    }

    OrganizationClient(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Организаций
     *
     * @param expand              поля, которые будут получены сразу во время запроса
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListEntity<OrganizationEntity> get(String... expand) throws IOException, LognexApiException {
        return get(api, OrganizationEntity.class, expand);
    }

    /**
     * Создание новой Организации
     *
     * @param newEntity данные новой сущности (<b>Внимание!</b> В этот объект после успешного
     *                  выполнения запроса будут записаны полученные от API данные!)
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public OrganizationEntity post(OrganizationEntity newEntity) throws IOException, LognexApiException {
        return post(api, newEntity, OrganizationEntity.class);
    }
}
