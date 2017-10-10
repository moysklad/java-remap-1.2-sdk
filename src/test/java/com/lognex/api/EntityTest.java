package com.lognex.api;

import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import org.junit.Test;


import java.util.List;

import static org.junit.Assert.*;

public class EntityTest {

    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"));

    @Test
    public void testCreateAndGetCounterparty() throws Exception{
        Counterparty cp = new Counterparty();
        cp.setName("Володя LOL PRO2");
        ApiResponse response1 =  api.entity("counterparty").create(cp).execute();
        assertTrue(response1.getStatus() == 200);
        assertFalse(response1.hasErrors());
        ID id = response1.getEntities().get(0).getId();

        ApiResponse response = api.entity("counterparty").id(id).read().execute();
        assertFalse(response.hasErrors());
        Counterparty counterparty = (Counterparty) response.getEntities().get(0);
        assertNotNull(counterparty.getId());
        assertEquals(counterparty.getName(), "Володя LOL PRO2");
    }

    @Test
    public void testCounterpartyWithAdditionaFields() throws Exception{
        ApiResponse response = api.entity("counterparty").list().execute();
        List<Counterparty> counterparties = (List<Counterparty>) response.getEntities();
        int i=2;
    }

}
