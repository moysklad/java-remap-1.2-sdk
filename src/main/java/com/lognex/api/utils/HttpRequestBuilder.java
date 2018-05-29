package com.lognex.api.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.lognex.api.LognexApi;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ErrorResponse;
import com.lognex.api.responses.ListResponse;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public final class HttpRequestBuilder {
    private static final Logger logger = LogManager.getLogger(HttpRequestBuilder.class);
    private static final Base64.Encoder b64enc = Base64.getEncoder();
    private static Charset queryParamsCharset = Charset.forName("UTF-8");

    private final String url;
    private final Map<String, Object> query;
    private final Map<String, Object> headers;
    private final Gson gson;
    private final CloseableHttpClient client;
    private Object body;

    private HttpRequestBuilder(CloseableHttpClient client, String url) {
        this.client = client;
        this.url = url;
        query = new HashMap<>();
        headers = new HashMap<>();
        body = null;
        gson = new GsonBuilder().create();
    }

    public static void setQueryParamsCharset(Charset queryParamsCharset) {
        HttpRequestBuilder.queryParamsCharset = queryParamsCharset;
    }

    /**
     * Создаёт билдер запроса к URL
     */
    public static HttpRequestBuilder url(LognexApi api, String url) {
        return new HttpRequestBuilder(api.getClient(), url).auth(api);
    }

    /**
     * Создаёт билдер запроса к методу API
     *
     * @param api  проинициализированный экземпляр класса с данными API
     * @param path путь к методу API (например <code>/entity/counterparty/metadata</code>)
     */
    public static HttpRequestBuilder path(LognexApi api, String path) {
        return new HttpRequestBuilder(api.getClient(), api.getHost() + LognexApi.API_PATH + path).auth(api);
    }

    /**
     * Добавляет авторизационный заголовок с данными доступа API
     */
    private HttpRequestBuilder auth(LognexApi api) {
        return this.header(
                "Authorization",
                "Basic " + b64enc.encodeToString((api.getLogin() + ":" + api.getPassword()).getBytes())
        );
    }

    /**
     * Добавить параметр в строку запроса после URL в формате <code>key=value&</code>
     */
    public HttpRequestBuilder query(String key, Object value) {
        query.put(key, value);
        return this;
    }

    /**
     * Добавить параметр в заголовки запроса
     */
    public HttpRequestBuilder header(String key, Object value) {
        headers.put(key, value);
        return this;
    }

    /**
     * Добавить тело запроса (для запросов, поддерживающих отправку данных в теле)
     */
    public HttpRequestBuilder body(Object o) {
        body = o;
        return this;
    }

    /**
     * Строит полный URL запроса с учётом добавленных ранее параметров запроса
     */
    private String getFullUrl() {
        StringBuilder queryBuilder = new StringBuilder();
        for (Map.Entry<String, Object> e : query.entrySet()) {
            if (queryBuilder.length() == 0) queryBuilder.append("&");
            try {
                queryBuilder.
                        append(URLEncoder.encode(e.getKey(), queryParamsCharset.name())).
                        append("=").
                        append(URLEncoder.encode(String.valueOf(e.getValue()), queryParamsCharset.name()));
            } catch (UnsupportedEncodingException e1) {
            }
        }

        return url + (queryBuilder.length() == 0 ? "" : "?" + queryBuilder.toString());
    }

    /**
     * Добавляет заголовки в запрос
     */
    private void applyHeaders(HttpUriRequest request) {
        for (Map.Entry<String, Object> e : headers.entrySet()) {
            request.setHeader(e.getKey(), String.valueOf(e.getValue()));
        }
    }

    /**
     * Выполняет созданный запрос
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    private String executeRequest(HttpUriRequest request) throws IOException, LognexApiException {
        logger.debug("Выполнение запроса  {} {}...", request.getMethod(), request.getURI());
        try (CloseableHttpResponse response = client.execute(request)) {
            String json = EntityUtils.toString(response.getEntity());
            logger.debug("Ответ на запрос     {} {}: {}", request.getMethod(), request.getURI(), json);

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

    /**
     * Выполняет GET-запрос с указанными ранее параметрами
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public String get() throws IOException, LognexApiException {
        HttpGet request = new HttpGet(getFullUrl());
        applyHeaders(request);
        return executeRequest(request);
    }

    /**
     * Выполняет GET-запрос с указанными ранее параметрами и конвертирует ответ в объект указанного класса
     *
     * @param cl класс, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public <T> T get(Class<T> cl) throws IOException, LognexApiException {
        return gson.fromJson(get(), cl);
    }

    /**
     * Выполняет GET-запрос с указанными ранее параметрами и конвертирует ответ в <b>массив</b> объектов указанного класса
     *
     * @param cl класс объектов массива, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public <T extends MetaEntity> ListResponse<T> list(Class<T> cl) throws IOException, LognexApiException {
        return gson.fromJson(get(), TypeToken.getParameterized(ListResponse.class, cl).getType());
    }

    /**
     * Выполняет POST-запрос с указанными ранее параметрами
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public String post() throws IOException, LognexApiException {
        HttpPost request = new HttpPost(getFullUrl());
        applyHeaders(request);

        if (body != null) {
            StringEntity requestEntity = new StringEntity(gson.toJson(body), ContentType.APPLICATION_JSON);
            request.setEntity(requestEntity);
        }

        return executeRequest(request);
    }

    /**
     * Выполняет POST-запрос с указанными ранее параметрами и конвертирует ответ в объект указанного класса
     *
     * @param cl класс, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public <T> T post(Class<T> cl) throws IOException, LognexApiException {
        return gson.fromJson(post(), cl);
    }

    /**
     * Выполняет DELETE-запрос с указанными ранее параметрами
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public void delete() throws IOException, LognexApiException {
        HttpDelete request = new HttpDelete(getFullUrl());
        applyHeaders(request);
        executeRequest(request);
    }

    /**
     * Выполняет PUT-запрос с указанными ранее параметрами
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public String put() throws IOException, LognexApiException {
        HttpPut request = new HttpPut(getFullUrl());
        applyHeaders(request);

        if (body != null) {
            StringEntity requestEntity = new StringEntity(gson.toJson(body), ContentType.APPLICATION_JSON);
            request.setEntity(requestEntity);
        }

        return executeRequest(request);
    }

    /**
     * Выполняет PUT-запрос с указанными ранее параметрами и конвертирует ответ в объект указанного класса
     *
     * @param cl класс, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws LognexApiException когда возникла ошибка API
     */
    public <T> T put(Class<? extends T> cl) throws IOException, LognexApiException {
        return gson.fromJson(put(), cl);
    }
}
