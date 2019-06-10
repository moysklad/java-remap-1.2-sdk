package com.lognex.api.entities;

import com.lognex.api.LognexApi;
import com.lognex.api.clients.ApiClient;
import com.lognex.api.utils.*;
import org.junit.After;
import org.junit.Before;

public abstract class EntityTestBase implements TestRandomizers, TestAsserts, TestUtils {
    protected LognexApi api, mockApi;
    protected MockHttpClient mockHttpClient;
    protected SimpleEntityFactory simpleEntityFactory;

    @Before
    public void init() {
        api = new LognexApi(
                System.getenv("API_HOST"),
                false, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );

        mockHttpClient = new MockHttpClient();
        mockApi = new LognexApi("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
        simpleEntityFactory = new SimpleEntityFactory(api);
    }

    @After
    public void antiLimits() throws InterruptedException {
        Thread.sleep(200); // Защита от лимитов
    }

    protected ApiClient entityClient() {
        return null;
    }

    protected Class<? extends MetaEntity> entityClass() {
        return null;
    }
}
