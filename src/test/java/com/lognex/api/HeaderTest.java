package com.lognex.api;

import com.lognex.api.request.MSRequestTest;
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
        MSRequestTest request = new MSRequestTest(api.entity(Type.DEMAND).list().addOption(RequestOption.DATE_TIME_WITH_MILLISECONDS));
        HttpUriRequest internalRequest = request.getHttpRequestInternal();
        Header[] headers = internalRequest.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-Format-Millisecond", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testPrettyPrint() {
        MSRequestTest request = new MSRequestTest(api.entity(Type.DEMAND).list().addOption(RequestOption.JSON_PRETTY_PRINT));
        HttpUriRequest internalRequest = request.getHttpRequestInternal();
        Header[] headers = internalRequest.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("Lognex-Pretty-Print-JSON", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testLognexPrecision() {
        MSRequestTest request = new MSRequestTest(api.entity(Type.DEMAND).list().addOption(RequestOption.OPERATION_PRICES_WITHOUT_ROUNDING));
        HttpUriRequest internalRequest = request.getHttpRequestInternal();
        Header[] headers = internalRequest.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-Precision", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testLognexGetContent() {
        MSRequestTest request = new MSRequestTest(api.entity(Type.DEMAND).list().addOption(RequestOption.PRINT_DOCUMENT_CONTENT));
        HttpUriRequest internalRequest = request.getHttpRequestInternal();
        Header[] headers = internalRequest.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-Get-Content", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }

    @Test
    public void testDisableWebhook() {
        MSRequestTest request = new MSRequestTest(api.entity(Type.DEMAND).list().addOption(RequestOption.WITHOUT_WEBHOOK_TRIGGER));
        HttpUriRequest internalRequest = request.getHttpRequestInternal();
        Header[] headers = internalRequest.getAllHeaders();
        assertEquals(1, headers.length);
        assertEquals("X-Lognex-WebHook-Disable", headers[0].getName());
        assertEquals("true", headers[0].getValue());
    }
}
