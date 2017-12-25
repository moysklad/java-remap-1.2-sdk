package com.lognex.api;

import com.lognex.api.request.RequestOption;
import com.lognex.api.util.Type;
import org.apache.http.Header;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HeaderTest {
    private ApiClient api = new ApiClient(System.getenv("login"), System.getenv("password"), null);

    @Test
    public void testLognexFormatMillisecond() {
        HttpUriRequest request = api.entity(Type.DEMAND).list().addOption(RequestOption.DATE_TIME_WITH_MILLISECONDS).buildRequest();
        Header[] headers = request.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-Format-Millisecond", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testPrettyPrint() {
        HttpUriRequest request = api.entity(Type.DEMAND).list().addOption(RequestOption.JSON_PRETTY_PRINT).buildRequest();
        Header[] headers = request.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("Lognex-Pretty-Print-JSON", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testLognexPrecision() {
        HttpUriRequest request = api.entity(Type.DEMAND).list().addOption(RequestOption.OPERATION_PRICES_WITHOUT_ROUNDING).buildRequest();
        Header[] headers = request.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-Precision", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testLognexGetContent() {
        HttpUriRequest request = api.entity(Type.DEMAND).list().addOption(RequestOption.PRINT_DOCUMENT_CONTENT).buildRequest();
        Header[] headers = request.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-Get-Content", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testDisableWebhook() {
        HttpUriRequest request = api.entity(Type.DEMAND).list().addOption(RequestOption.WITHOUT_WEBHOOK_TRIGGER).buildRequest();
        Header[] headers = request.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-WebHook-Disable", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }
}
