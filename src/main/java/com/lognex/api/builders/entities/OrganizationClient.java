package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.builders.entities.endpoints.GetListEndpoint;
import com.lognex.api.builders.entities.endpoints.PostEndpoint;
import com.lognex.api.entities.Organization;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class OrganizationClient implements GetListEndpoint<Organization>, PostEndpoint<Organization> {
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
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    @Override
    public ListResponse<Organization> get() throws IOException, LognexApiException {
        return get(api, Organization.class);
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
    public void post(Organization newEntity) throws IOException, LognexApiException {
        post(api, newEntity, Organization.class);
    }
}
