package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.MockHttpClient;
import org.junit.Test;

import java.io.IOException;

import static com.lognex.api.LognexApi.API_PATH;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class RetailStoreEntityTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, LognexApiException {
        ListEntity<RetailStoreEntity> retailStoreList = api.entity().retailstore().get(filterEq("name", "Точка продаж"));
        assertEquals(1, retailStoreList.getRows().size());
        RetailStoreEntity e = retailStoreList.getRows().get(0);
        assertEquals("Точка продаж", e.getName());
        assertTrue(e.getDiscountEnable());
        assertEquals(Integer.valueOf(100), e.getDiscountMaxPercent());

        RetailStoreEntity retrievedEntity = api.entity().retailstore().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().retailstore().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        String id = randomString();
        mockApi.entity().retailstore().delete(id);

        MockHttpClient client = (MockHttpClient) mockApi.getClient();
        assertEquals(mockApi.getHost() + API_PATH + "/entity/retailstore/" + id,
                client.getLastExecutedRequest().getRequestLine().getUri()
        );
        assertEquals("DELETE", client.getLastExecutedRequest().getRequestLine().getMethod());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        RetailStoreEntity e = new RetailStoreEntity();
        String id = randomString();
        e.setId(id);

        mockApi.entity().retailstore().delete(e);

        MockHttpClient client = (MockHttpClient) mockApi.getClient();
        assertEquals(mockApi.getHost() + API_PATH + "/entity/retailstore/" + id,
                client.getLastExecutedRequest().getRequestLine().getUri()
        );
        assertEquals("DELETE", client.getLastExecutedRequest().getRequestLine().getMethod());
    }

    private void getAsserts(RetailStoreEntity e, RetailStoreEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDiscountEnable(), retrievedEntity.getDiscountEnable());
        assertEquals(e.getDiscountMaxPercent(), retrievedEntity.getDiscountMaxPercent());
    }
}
