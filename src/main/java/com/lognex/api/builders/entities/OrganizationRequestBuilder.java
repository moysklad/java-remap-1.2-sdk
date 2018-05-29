package com.lognex.api.builders.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.entities.Organization;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.HttpRequestBuilder;
import com.lognex.api.utils.LognexApiException;

import java.io.IOException;

public final class OrganizationRequestBuilder {
    private final LognexApi api;

    OrganizationRequestBuilder(LognexApi api) {
        this.api = api;
    }

    /**
     * Получение списка всех Организаций
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public ListResponse<Organization> get() throws IOException, LognexApiException {
        return HttpRequestBuilder.
                path(api, "/entity/organization").
                list(Organization.class);
    }
}
