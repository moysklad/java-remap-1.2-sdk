package com.lognex.api.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.utils.MockHttpClient;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Before;

public abstract class EntityTestBase implements TestRandomizers, TestAsserts {
    protected LognexApi api, mockApi;
    protected MockHttpClient mockHttpClient;

    @Before
    public void init() {
        api = new LognexApi(
                System.getenv("API_HOST"),
                false, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );

        mockHttpClient = new MockHttpClient();
        mockApi = new LognexApi("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
    }
}
