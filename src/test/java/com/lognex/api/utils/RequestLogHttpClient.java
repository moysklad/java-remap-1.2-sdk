package com.lognex.api.utils;

import lombok.Getter;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

public class RequestLogHttpClient extends CloseableHttpClient {
    private final CloseableHttpClient client;

    @Getter
    private HttpRequest lastExecutedRequest;

    public RequestLogHttpClient() {
        client = HttpClients.createDefault();
    }

    @Override
    protected CloseableHttpResponse doExecute(HttpHost httpHost, HttpRequest httpRequest, HttpContext httpContext) throws IOException, ClientProtocolException {
        lastExecutedRequest = httpRequest;
        return client.execute(httpHost, httpRequest, httpContext);
    }

    @Override
    public void close() throws IOException {
        client.close();
    }

    @Override
    public HttpParams getParams() {
        return client.getParams();
    }

    @Override
    public ClientConnectionManager getConnectionManager() {
        return client.getConnectionManager();
    }

    public void reset() {
        lastExecutedRequest = null;
    }
}