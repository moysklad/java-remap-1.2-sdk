package com.lognex.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lognex.api.LognexApi;
import com.lognex.api.responses.ErrorResponse;
import com.lognex.api.responses.ListResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public final class HttpRequestBuilder {
    private final LognexApi api;
    private Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private String baseUrl;
    private final Map<String, Object> query;
    private final Map<String, Object> headers;
    private Object body;

    private HttpRequestBuilder(LognexApi api, String url, boolean prependHost) {
        this.api = api;
        this.baseUrl = (prependHost ? (api.getHost() + LognexApi.API_PATH) : "") + url;
        query = new HashMap<>();
        headers = new HashMap<>();
        body = null;
    }

    public static HttpRequestBuilder url(LognexApi api, String url) {
        return new HttpRequestBuilder(api, url, false);
    }

    public static HttpRequestBuilder path(LognexApi api, String url) {
        return new HttpRequestBuilder(api, url, true);
    }

    public HttpRequestBuilder query(String key, Object value) {
        query.put(key, value);
        return this;
    }

    public HttpRequestBuilder header(String key, Object value) {
        headers.put(key, value);
        return this;
    }

    public HttpRequestBuilder body(Object o) {
        body = o;
        return this;
    }

    private String getFullUrl() {
        StringBuilder queryBuilder = new StringBuilder();
        for (Map.Entry<String, Object> e : query.entrySet()) {
            if (queryBuilder.length() == 0) queryBuilder.append("&");
            try {
                queryBuilder.append(URLEncoder.encode(e.getKey(), "UTF-8")).
                        append("=").
                        append(URLEncoder.encode(String.valueOf(e.getValue()), "UTF-8"));
            } catch (UnsupportedEncodingException e1) {
            }
        }

        return baseUrl + (queryBuilder.length() == 0 ? "" : "&" + queryBuilder.toString());
    }

    public String get() throws IOException, LognexApiException {
        HttpGet request = new HttpGet(getFullUrl());
        header("Authorization", "Basic YWRtaW5AY2RkZWU1MDY2N2FmZmNmOjFFM2I4MURlM2NiMjRFNWE=");

        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.setHeader(e.getKey(), String.valueOf(e.getValue()));
        }

        try (CloseableHttpResponse response = api.getClient().execute(request)) {
            String json = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 200) {
                ErrorResponse er = gson.fromJson(json, ErrorResponse.class);

                throw new LognexApiException(
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
                        er
                );
            }

            return json;
        }
    }

    public <T> T get(Class<T> cl) throws IOException, LognexApiException {
        return gson.fromJson(get(), cl);
    }

    public <T> ListResponse<T> getListResponse(Class<T> cl) throws IOException, LognexApiException {
        return gson.fromJson(get(), TypeToken.getParameterized(ListResponse.class, cl).getType());
    }

    public String post() throws IOException, LognexApiException {
        HttpPost request = new HttpPost(getFullUrl());
        header("Authorization", "Basic YWRtaW5AY2RkZWU1MDY2N2FmZmNmOjFFM2I4MURlM2NiMjRFNWE=");

        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.setHeader(e.getKey(), String.valueOf(e.getValue()));
        }

        if (body != null) {
            StringEntity requestEntity = new StringEntity(gson.toJson(body), ContentType.APPLICATION_JSON);
            request.setEntity(requestEntity);
        }

        try (CloseableHttpResponse response = api.getClient().execute(request)) {
            String json = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 200) {
                ErrorResponse er = gson.fromJson(json, ErrorResponse.class);

                throw new LognexApiException(
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
                        er
                );
            }

            return json;
        }
    }

    public <T> T post(Class<T> cl) throws IOException, LognexApiException {
        return gson.fromJson(post(), cl);
    }

    public void delete() throws IOException, LognexApiException {
        HttpDelete request = new HttpDelete(getFullUrl());
        header("Authorization", "Basic YWRtaW5AY2RkZWU1MDY2N2FmZmNmOjFFM2I4MURlM2NiMjRFNWE=");

        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.setHeader(e.getKey(), String.valueOf(e.getValue()));
        }

        try (CloseableHttpResponse response = api.getClient().execute(request)) {
            String json = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 200) {
                ErrorResponse er = gson.fromJson(json, ErrorResponse.class);

                throw new LognexApiException(
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
                        er
                );
            }
        }
    }

    public String put() throws IOException, LognexApiException {
        HttpPut request = new HttpPut(getFullUrl());
        header("Authorization", "Basic YWRtaW5AY2RkZWU1MDY2N2FmZmNmOjFFM2I4MURlM2NiMjRFNWE=");

        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.setHeader(e.getKey(), String.valueOf(e.getValue()));
        }

        if (body != null) {
            StringEntity requestEntity = new StringEntity(gson.toJson(body), ContentType.APPLICATION_JSON);
            request.setEntity(requestEntity);
        }

        try (CloseableHttpResponse response = api.getClient().execute(request)) {
            String json = EntityUtils.toString(response.getEntity());

            if (response.getStatusLine().getStatusCode() != 200) {
                ErrorResponse er = gson.fromJson(json, ErrorResponse.class);

                throw new LognexApiException(
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
                        er
                );
            }

            return json;
        }
    }

    public <T> T put(Class<? extends T> cl) throws IOException, LognexApiException {
        return gson.fromJson(put(), cl);
    }
}
