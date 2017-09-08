package com.lognex.api;

import com.lognex.api.endpoint.DocumentEndpoint;
import com.lognex.api.exception.ConverterException;
import com.lognex.api.model.document.PaymentIn;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertFalse;

public class DocumentEndpointTest {
    private static final DocumentEndpoint documentEndpoint = new DocumentEndpoint();

    @Test
    public void testReadPaymentsIn() throws ConverterException {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("admin@api-sdk", "apisdkapisdk");
        List<PaymentIn> paymentIns = documentEndpoint.readPaymentInList(requestBuilder);
        assertFalse(paymentIns.stream()
                .anyMatch(p -> p.getId() == null));
    }
}
