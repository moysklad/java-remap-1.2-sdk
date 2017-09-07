package com.lognex.api;

import com.lognex.api.endpoint.DocumentEndpoint;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.fail;

public class RequestBuilderTest {
    private static final DocumentEndpoint documentEnpoint = new DocumentEndpoint();

    @Test
    public void testWithoutParams() {
        API api = new API();
        API.RequestBuilder requestBuilder = api.initRequest("Login", "password");
        try {
            requestBuilder.read(documentEnpoint);
            fail();
        } catch (NullPointerException e) {
            TestCase.assertEquals("type is missing", e.getMessage());
        }
    }
}
