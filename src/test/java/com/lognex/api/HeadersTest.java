package com.lognex.api;

import com.lognex.api.entities.Template;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.MockHttpClient;
import org.apache.http.HttpRequest;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class HeadersTest {
    private ApiClient api;
    private MockHttpClient mockHttpClient;

    @Before
    public void init() {
        mockHttpClient = new MockHttpClient();
    }

    @Test
    public void authHeaderTest() throws IOException, ApiClientException {
        api = new ApiClient("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
        api.entity().counterparty().get();

        HttpRequest req = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req);
        assertEquals("Basic W0FQSV9MT0dJTl06W0FQSV9QQVNTV09SRF0=", req.getFirstHeader("Authorization").getValue());
    }

    @Test
    public void prettyPrintHeaderTest() throws IOException, ApiClientException {
        api = new ApiClient("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
        api.entity().counterparty().get();

        HttpRequest req0 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req0);
        assertNull(req0.getFirstHeader("Lognex-Pretty-Print-JSON"));

        api.prettyPrintJson();
        api.entity().counterparty().get();

        HttpRequest req1 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req1);
        assertEquals("true", req1.getFirstHeader("Lognex-Pretty-Print-JSON").getValue());

        api.prettyPrintJson(false);
        api.entity().counterparty().get();

        HttpRequest req2 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req2);
        assertNull(req2.getFirstHeader("Lognex-Pretty-Print-JSON"));
    }

    @Test
    public void precisionHeaderTest() throws IOException, ApiClientException {
        api = new ApiClient("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
        api.entity().counterparty().get();

        HttpRequest req0 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req0);
        assertNull(req0.getFirstHeader("X-Lognex-Precision"));

        api.precision();
        api.entity().counterparty().get();

        HttpRequest req1 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req1);
        assertEquals("true", req1.getFirstHeader("X-Lognex-Precision").getValue());

        api.precision(false);
        api.entity().counterparty().get();

        HttpRequest req2 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req2);
        assertNull(req2.getFirstHeader("X-Lognex-Precision"));
    }

    @Test
    public void webhookHeaderTest() throws IOException, ApiClientException {
        api = new ApiClient("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
        api.entity().counterparty().get();

        HttpRequest req0 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req0);
        assertNull(req0.getFirstHeader("X-Lognex-WebHook-Disable"));

        api.withoutWebhookContent();
        api.entity().counterparty().get();

        HttpRequest req1 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req1);
        assertEquals("true", req1.getFirstHeader("X-Lognex-WebHook-Disable").getValue());

        api.withoutWebhookContent(false);
        api.entity().counterparty().get();

        HttpRequest req2 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req2);
        assertNull(req2.getFirstHeader("X-Lognex-WebHook-Disable"));
    }

    @Test
    public void printDocumentHeaderTest() throws IOException, ApiClientException {
        api = new ApiClient("test.moysklad", true, "[API_LOGIN]", "[API_PASSWORD]", mockHttpClient);
        api.entity().demand().export("ID", new Template(), new File("test0.xls"));

        HttpRequest req0 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req0);
        assertNull(req0.getFirstHeader("X-Lognex-Get-Content"));

        api.entity().demand().export("ID", new Template(), new File("test1.xls"), true);

        HttpRequest req1 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req1);
        assertEquals("true", req1.getFirstHeader("X-Lognex-Get-Content").getValue());

        api.entity().demand().export("ID", new Template(), new File("test2.xls"), false);

        HttpRequest req2 = mockHttpClient.getLastExecutedRequest();
        assertNotNull(req2);
        assertNull(req2.getFirstHeader("X-Lognex-Get-Content"));
    }
}
