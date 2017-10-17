package com.lognex.api;

import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DocumentEndpointTest {
    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"), null);

    @Test
    public void testReadPaymentsIn() throws ConverterException {
        ApiResponse response = api.entity(Type.PAYMENT_IN).list().limit(10).execute();
        assertFalse(response.hasErrors());
        assertTrue(response.getEntities().size() <= 10);
        response.getEntities().forEach(e -> assertNotNull(e.getId()));
    }

    @Test
    public void testReadPaymentIn() throws Exception{
        ApiResponse response = api.entity(Type.PAYMENT_IN).id(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0")).read().execute();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntities().size(), 1);
        assertEquals(response.getEntities().get(0).getId(), new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"));
    }

    @Test
    public void testReadPaymentInWithAgentAccountExpand() throws ConverterException {
        ApiResponse response = api.
                entity(Type.PAYMENT_IN).
                id(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"))
                .read().addExpand("agentAccount").execute();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntities().size(), 1);
        assertEquals(response.getEntities().get(0).getId(), new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"));
        assertEquals(((PaymentIn)response.getEntities().get(0)).getAgentAccount().getId(), new ID("5bc8549b-9e14-11e7-7a34-5acf00403d35"));
    }

    @Test
    public void testReadPaymentsInWithAgentAccountExpand() throws Exception {
        ApiResponse response = api.entity(Type.PAYMENT_IN).list().addExpand("agentAccount").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() > 0);
        response.getEntities().stream()
                .map(o -> (PaymentIn)o)
                .filter(p -> p.getAgentAccount() != null)
                .forEach(p -> assertTrue(p.getAgentAccount().getId() != null));
    }

    @Test
    public void testReadPaymentInWithAgentExpand() throws Exception {
        ApiResponse response = api.entity(Type.PAYMENT_IN).id(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"))
                .read().addExpand("agent").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() == 1);
        assertEquals(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"), response.getEntities().get(0).getId());
        assertEquals(new ID("81c97d10-9482-11e7-7a6c-d2a9000847cc"), ((PaymentIn)response.getEntities().get(0)).getAgent().getId());


    }

    @Test
    public void testCounterpartyWithAccountsExpand() throws Exception {
        ApiResponse response = api.entity(Type.COUNTERPARTY).id(new ID("4fedbd4c-b26d-11e7-7a6c-d2a9002d0b23"))
                .read().addExpand("accounts").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() == 1);
        Counterparty counterparty = ((Counterparty) response.getEntities().get(0));
        assertFalse(counterparty.getAccounts().isEmpty());
    }
}
