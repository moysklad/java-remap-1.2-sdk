package com.lognex.api.builders.entities.document;

import com.lognex.api.LognexApi;

public class DocumentCustomerOrderByIdRequestBuilder {
    private final LognexApi api;
    private final String id;

    public DocumentCustomerOrderByIdRequestBuilder(LognexApi api, String id) {
        this.api = api;
        this.id = id;
    }

    public void get() {

    }
}
