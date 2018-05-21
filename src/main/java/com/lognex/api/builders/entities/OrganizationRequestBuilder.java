package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Organization;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class OrganizationRequestBuilder {
    private final LognexApi api;

    public OrganizationRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получить список всех Организаций
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка при обращении в API
     */
    public ListResponse<Organization> get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/organization").
                getListResponse(Organization.class);
    }
}
