package com.lognex.api;

import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.response.ApiResponse;
import com.lognex.api.util.ID;
import com.lognex.api.util.Type;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DocumentEndpointTest {
    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"));

    @Test
    public void testReadPaymentsIn() throws ConverterException {
        ApiResponse response = api.entity(Type.PAYMENTIN).list().limit(10).execute();
        Assert.assertFalse(response.hasErrors());
        assertEquals(10, response.getEntities().size());
        response.getEntities().forEach(e -> assertNotNull(e.getId()));
    }

    @Test
    public void testReadPaymentIn() throws Exception{
        ApiResponse response = api.entity(Type.PAYMENTIN).id(new ID("017d451a-5acf-43e9-b8e1-e91ccc339d59")).read().execute();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntities().size(), 1);
        assertEquals(response.getEntities().get(0).getId(), new ID("017d451a-5acf-43e9-b8e1-e91ccc339d59"));
    }

    @Test
    public void testReadPaymentInWithAgentAccountExpand() throws ConverterException {
        ApiResponse response = api.
                entity(Type.PAYMENTIN).
                id(new ID("9671ada7-735c-11e7-7a69-9711000111d6"))
                .read().addExpand("agentAccount").execute();
        assertEquals(response.getStatus(), 200);
        assertEquals(response.getEntities().size(), 1);
        assertEquals(response.getEntities().get(0).getId(), new ID("9671ada7-735c-11e7-7a69-9711000111d6"));
        assertEquals(((PaymentIn)response.getEntities().get(0)).getAgentAccount().getId(), new ID("3f4de04d-e5dc-11e3-a77a-002590a28eca"));
    }

    @Test
    public void testReadPaymentsInWithAgentAccountExpand() throws Exception {
        ApiResponse response = api.entity(Type.PAYMENTIN).list().addExpand("agentAccount").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() > 0);
        response.getEntities().stream()
                .map(o -> (PaymentIn)o)
                .filter(p -> p.getAgentAccount() != null)
                .forEach(p -> assertTrue(p.getAgentAccount().getId() != null));
    }

    @Test
    public void testReadPaymentInWithAgentExpand() throws Exception {
        ApiResponse response = api.entity(Type.PAYMENTIN).id(new ID("9671ada7-735c-11e7-7a69-9711000111d6"))
                .read().addExpand("agent").execute();
        assertEquals(response.getStatus(), 200);
        assertTrue(response.getEntities().size() == 1);
        assertEquals(new ID("9671ada7-735c-11e7-7a69-9711000111d6"), response.getEntities().get(0).getId());
        assertEquals(new ID("65cc16a7-276a-451c-995f-0ecfc44f72f0"), ((PaymentIn)response.getEntities().get(0)).getAgent().getId());


    }
}
