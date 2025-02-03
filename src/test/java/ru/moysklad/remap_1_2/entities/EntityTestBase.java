package ru.moysklad.remap_1_2.entities;

import org.junit.After;
import org.junit.Before;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.utils.*;

public abstract class EntityTestBase implements TestRandomizers, TestAsserts, TestUtils, IEntityTestBase {
    protected ApiClient api, mockApi;
    protected MockHttpClient mockHttpClient;
    protected SimpleEntityManager simpleEntityManager;

    @Before
    public void init() {
        api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
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

    public SimpleEntityManager getSimpleEntityManager() {
        return simpleEntityManager;
    }
}
