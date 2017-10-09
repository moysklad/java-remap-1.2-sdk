package com.lognex.api;

import com.lognex.api.endpoint.ApiClient;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class EntityTest {

    private ApiClient api = new ApiClient("tut@tutorial", "tutorial1");

    @Test
    public void testCreateAndGetCounterparty() throws Exception{
        Counterparty cp = new Counterparty();
        cp.setName("Володя");
        ApiResponse response1 =  api.entity("counterparty").create(cp).execute();

        ApiResponse listResponse = api.entity("counterparty").list().execute();
        assertFalse(listResponse.hasErrors());
        List<Counterparty> counterpartyList = (List<Counterparty>) listResponse.getEntities();
        assertFalse(counterpartyList.isEmpty());
        assertEquals(counterpartyList.size(), 1);
        assertNotNull(counterpartyList.get(0).getId());
        ID id = counterpartyList.get(0).getId();

        ApiResponse response = api.entity("counterparty").id(id).read().execute();
        assertFalse(response.hasErrors());
        Counterparty counterparty = (Counterparty) response.getEntities();
        assertNotNull(counterparty.getId());
        assertEquals(counterparty.getName(), "Володя");
    }

}
