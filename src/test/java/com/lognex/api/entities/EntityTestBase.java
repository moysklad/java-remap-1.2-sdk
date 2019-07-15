package com.lognex.api.entities;

import com.lognex.api.ApiClient;
import com.lognex.api.utils.*;
import org.junit.After;
import org.junit.Before;

public abstract class EntityTestBase implements TestRandomizers, TestAsserts, TestUtils {
    protected ApiClient api, mockApi;
    protected MockHttpClient mockHttpClient;
    protected SimpleEntityManager simpleEntityManager;

    @Before
    public void init() {
        api = new ApiClient(
                System.getenv("API_HOST"),
                true, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );

        mockHttpClient = new MockHttpClient();
        mockApi = new ApiClient("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
        simpleEntityManager = new SimpleEntityManager(api);
    }

    @After
    public void tearDown() {
        simpleEntityManager.clearAccessCounts();
    }

    @After
    public void antiLimits() throws InterruptedException {
        Thread.sleep(200); // Защита от лимитов
    }

    protected com.lognex.api.clients.ApiClient entityClient() {
        return null;
    }

    protected Class<? extends MetaEntity> entityClass() {
        return null;
    }
}
