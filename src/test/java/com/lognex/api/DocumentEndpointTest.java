package com.lognex.api;

import com.lognex.api.endpoint.DocumentEndpoint;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.util.ID;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;

public class DocumentEndpointTest {
    private static final DocumentEndpoint documentEndpoint = new DocumentEndpoint();

    @Test
    public void testReadPaymentsIn() throws ConverterException {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest(System.getenv("login"), System.getenv("password"));
        List<PaymentIn> paymentIns = documentEndpoint.readPaymentInList(requestBuilder);
        assertFalse(paymentIns.stream()
                .anyMatch(p -> p.getId() == null));
    }

    @Test
    public void testReadPaymentIn() throws ConverterException {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest(System.getenv("login"), System.getenv("password"))
                .id("ac08418c-9482-11e7-7a69-8f550003b1e0");
        PaymentIn paymentIn = documentEndpoint.readPaymentIn(requestBuilder);
        assertEquals(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"), paymentIn.getId());

    }

    @Test
    public void testReadPaymentInWithAgentAccountExpand() throws ConverterException {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest(System.getenv("login"), System.getenv("password"))
                .id("ac08418c-9482-11e7-7a69-8f550003b1e0")
                .expand("agentAccount");
        PaymentIn paymentIn = documentEndpoint.readPaymentIn(requestBuilder);
        assertEquals(new ID("ac08418c-9482-11e7-7a69-8f550003b1e0"), paymentIn.getId());
        assertEquals(new ID("81c97f7f-9482-11e7-7a6c-d2a9000847cd"), paymentIn.getAgentAccount().getId());
    }

    @Test
    public void testReadPaymentsInWithAgentAccountExpand() throws ConverterException {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest(System.getenv("login"), System.getenv("password"))
                .expand("agentAccount");
        List<PaymentIn> paymentIns = documentEndpoint.readPaymentInList(requestBuilder);
        assertFalse(paymentIns.stream()
                .anyMatch(p -> p.getId() == null));
        assertFalse(paymentIns.stream()
                .map(PaymentIn::getAgentAccount)
                .anyMatch(a -> a.getId() == null));
    }
}
