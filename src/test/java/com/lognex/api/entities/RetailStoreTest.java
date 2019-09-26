package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.MockHttpClient;
import org.junit.Test;

import java.io.IOException;

import static com.lognex.api.utils.Constants.API_PATH;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RetailStoreTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, ApiClientException {
        ListEntity<RetailStore> retailStoreList = api.entity().retailstore().get(filterEq("name", "Точка продаж"));
        assertEquals(1, retailStoreList.getRows().size());
        RetailStore retailStore = retailStoreList.getRows().get(0);
        assertEquals("Точка продаж", retailStore.getName());
        assertTrue(retailStore.getDiscountEnable());
        assertEquals(Integer.valueOf(100), retailStore.getDiscountMaxPercent());

        RetailStore retrievedEntity = api.entity().retailstore().get(retailStore.getId());
        getAsserts(retailStore, retrievedEntity);

        retrievedEntity = api.entity().retailstore().get(retailStore);
        getAsserts(retailStore, retrievedEntity);
    }

    @Test
    public void updateTest() throws IOException, ApiClientException {
        ListEntity<RetailStore> retailStoreList = api.entity().retailstore().get(filterEq("name", "Точка продаж"));
        assertEquals(1, retailStoreList.getRows().size());
        RetailStore retailStore = retailStoreList.getRows().get(0);
        Address addressFull = randomAddress(api);
        retailStore.setAddressFull(addressFull);
        api.entity().retailstore().update(retailStore.getId(), retailStore);
        assertAddressFull(addressFull, retailStore.getAddressFull());
    }

    @Test
    public void deleteTest() throws IOException, ApiClientException {
        String id = randomString();
        mockApi.entity().retailstore().delete(id);

        MockHttpClient client = (MockHttpClient) mockApi.getClient();
        assertEquals(mockApi.getHost() + API_PATH + "/entity/retailstore/" + id,
                client.getLastExecutedRequest().getRequestLine().getUri()
        );
        assertEquals("DELETE", client.getLastExecutedRequest().getRequestLine().getMethod());
    }

    @Test
    public void deleteByIdTest() throws IOException, ApiClientException {
        RetailStore retailStore = new RetailStore();
        String id = randomString();
        retailStore.setId(id);

        mockApi.entity().retailstore().delete(retailStore);

        MockHttpClient client = (MockHttpClient) mockApi.getClient();
        assertEquals(mockApi.getHost() + API_PATH + "/entity/retailstore/" + id,
                client.getLastExecutedRequest().getRequestLine().getUri()
        );
        assertEquals("DELETE", client.getLastExecutedRequest().getRequestLine().getMethod());
    }

    private void getAsserts(RetailStore retailStore, RetailStore retrievedEntity) {
        assertEquals(retailStore.getName(), retrievedEntity.getName());
        assertEquals(retailStore.getDiscountEnable(), retrievedEntity.getDiscountEnable());
        assertEquals(retailStore.getDiscountMaxPercent(), retrievedEntity.getDiscountMaxPercent());
    }
}
