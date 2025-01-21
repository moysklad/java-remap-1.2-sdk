package ru.moysklad.remap_1_2.utils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ErrorResponse;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.*;
import java.util.stream.Collectors;

import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;
import static org.apache.commons.lang3.StringUtils.isBlank;


public final class HttpRequestExecutor {
    private static final Logger logger = LoggerFactory.getLogger(HttpRequestExecutor.class);
    private static final Base64.Encoder b64enc = Base64.getEncoder();
    private static Charset queryParamsCharset = Charset.forName("UTF-8");

    private final String hostApiPath;
    private final String url;
    private List<ApiParam> apiParams;
    private Map<String, Object> query;
    private Map<String, Object> headers;
    private final ObjectMapper objectMapper;
    private final CloseableHttpClient client;
    private Object body;

    private HttpRequestExecutor(ApiClient api, String url) {
        if (api == null) {
            throw new IllegalArgumentException("Для выполнения запроса к API нужен проинициализированный экземпляр ApiClient!");
        }

        this.client = api.getClient();
        this.hostApiPath = api.getHost() + API_PATH;
        this.url = hostApiPath + url;
        query = new HashMap<>();
        headers = createRequiredHeadersMap();
        body = null;
        auth(api);

        if (api.isPrettyPrintJson()) header("Lognex-Pretty-Print-JSON", "true");
        if (api.isPricePrecision()) header("X-Lognex-Precision", "true");
        if (api.isWithoutWebhookContent()) header("X-Lognex-WebHook-Disable", "true");

        objectMapper = ApiClient.createObjectMapper();
    }

    private HttpRequestExecutor(CloseableHttpClient client, String url) {
        if (client == null)
            throw new IllegalArgumentException("Для выполнения запроса нужен проинициализированный экземпляр CloseableHttpClient!");

        this.client = client;
        this.hostApiPath = ""; // TODO maybe parse url, but it is not necessary until where is no apiParams() calls after url(). Now used only in entity fetch()
        this.url = url;
        query = new HashMap<>();
        headers = createRequiredHeadersMap();
        body = null;
        objectMapper = ApiClient.createObjectMapper();
    }

    /**
     * Задаёт кодировку параметров запроса
     */
    public static void setQueryParamsCharset(Charset queryParamsCharset) {
        HttpRequestExecutor.queryParamsCharset = queryParamsCharset;
    }

    /**
     * Создаёт билдер запроса к URL
     */
    public static HttpRequestExecutor url(ApiClient api, String url) {
        return new HttpRequestExecutor(api.getClient(), url).auth(api);
    }

    /**
     * Создаёт билдер запроса к методу API
     *
     * @param api  проинициализированный экземпляр класса с данными API
     * @param path путь к методу API (например <code>/entity/counterparty/metadata</code>)
     */
    public static HttpRequestExecutor path(ApiClient api, String path) {
        return new HttpRequestExecutor(api, path);
    }

    /**
     * Добавляет авторизационный заголовок с данными доступа API
     */
    private HttpRequestExecutor auth(ApiClient api) {
        if (!isBlank(api.getToken())) {
            return this.header("Authorization", "Bearer " + api.getToken());
        }
        return this.header(
                "Authorization",
                "Basic " + b64enc.encodeToString((api.getLogin() + ":" + api.getPassword()).getBytes())
        );
    }

    /**
     * Добавить параметр в строку запроса после URL в формате <code>key=value&amp;</code>.
     */
    public HttpRequestExecutor query(String key, Object value) {
        if (query == null) query = new HashMap<>();
        query.put(key, value);
        return this;
    }

    /**
     * Добавить параметр в заголовки запроса
     */
    public HttpRequestExecutor header(String key, Object value) {
        if (headers == null) headers = createRequiredHeadersMap();
        headers.put(key, value);
        return this;
    }

    /**
     * Добавить параметр API (например order, filter, offset и т. п.)
     */
    public HttpRequestExecutor apiParams(ApiParam... params) {
        if (params != null && params.length > 0) {
            if (apiParams == null) apiParams = new ArrayList<>();
            Collections.addAll(apiParams, params);
        }

        return this;
    }

    /**
     * Добавить тело запроса (для запросов, поддерживающих отправку данных в теле)
     */
    public HttpRequestExecutor body(Object o) {
        body = o;
        return this;
    }

    /**
     * Строит полный URL запроса с учётом добавленных ранее параметров запроса
     */
    private String getFullUrl() {
        if (apiParams != null && !apiParams.isEmpty()) {
            Map<ApiParam.Type, List<ApiParam>> pm = apiParams.stream().collect(Collectors.groupingBy(ApiParam::getType));
            for (Map.Entry<ApiParam.Type, List<ApiParam>> e : pm.entrySet()) {
                query(
                        e.getKey().name(),
                        ApiParam.renderStringQueryFromList(e.getKey(), e.getValue(), hostApiPath)
                );
            }
        }

        if (query == null || query.isEmpty()) return url;

        StringBuilder queryBuilder = new StringBuilder();
        for (Map.Entry<String, Object> e : query.entrySet()) {
            if (queryBuilder.length() != 0) queryBuilder.append("&");
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
     * @throws ApiClientException когда возникла ошибка API
     */
    private String executeRequest(HttpUriRequest request) throws IOException, ApiClientException {
        logger.debug("Выполнение запроса  {} {}...", request.getMethod(), request.getURI());
        try (CloseableHttpResponse response = client.execute(request)) {
            String json = response.getStatusLine().getStatusCode() == 204 ?
                    "" :
                    EntityUtils.toString(response.getEntity());

            if (isOkResponse(response)) {
                logger.debug(
                        "Ответ на запрос     {} {}: ({}) {}",
                        request.getMethod(),
                        request.getURI(),
                        response.getStatusLine().getStatusCode(),
                        json
                );
            } else {
                logger.info(
                        "Ошибка при запросе {} {}: ({}) {}",
                        request.getMethod(),
                        request.getURI(),
                        response.getStatusLine().getStatusCode(),
                        json
                );

                ErrorResponse er = objectMapper.readValue(json, ErrorResponse.class);

                throw new ApiClientException(
                        request.getMethod() + " " + request.getURI(),
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
                        er
                );
            }

            return json;
        }
    }

    /**
     * Выполняет созданный запрос и возвращает ответ в виде массива байтов
     *
     * @return тело ответа в виде массива байтов
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    private byte[] executeByteRequest(HttpUriRequest request) throws IOException, ApiClientException {
        logger.debug("Выполнение запроса  {} {}...", request.getMethod(), request.getURI());
        try (CloseableHttpResponse response = client.execute(request)) {
            byte[] bytes = EntityUtils.toByteArray(response.getEntity());

            if (isOkResponse(response)) {
                logger.debug(
                        "Ответ на запрос     {} {}: ({}) [bytes...]",
                        request.getMethod(),
                        request.getURI(),
                        response.getStatusLine().getStatusCode()
                );
            } else {
                String json = new String(bytes);

                logger.info(
                        "Ошибка при запросе {} {}: ({}) {}",
                        request.getMethod(),
                        request.getURI(),
                        response.getStatusLine().getStatusCode(),
                        json
                );

                ErrorResponse er = objectMapper.readValue(json, ErrorResponse.class);

                throw new ApiClientException(
                        request.getMethod() + " " + request.getURI(),
                        response.getStatusLine().getStatusCode(),
                        response.getStatusLine().getReasonPhrase(),
                        er
                );
            }

            return bytes;
        }
    }

    private boolean isOkResponse(CloseableHttpResponse response) {
        final int statusCode = response.getStatusLine().getStatusCode();
        return statusCode == 200 ||
                statusCode == 201 ||
                statusCode == 204;
    }

    private Map<String, Object> createRequiredHeadersMap() {
        return new HashMap<>(Collections.singletonMap("Accept-Encoding", "gzip"));
    }

    /**
     * Выполняет GET-запрос с указанными ранее параметрами
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public String get() throws IOException, ApiClientException {
        HttpGet request = new HttpGet(getFullUrl());
        applyHeaders(request);
        return executeRequest(request);
    }

    /**
     * Выполняет GET-запрос с указанными ранее параметрами и конвертирует ответ в объект указанного класса
     *
     * @param cl класс, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public <T> T get(Class<T> cl) throws IOException, ApiClientException {
        return objectMapper.readValue(get(), cl);
    }

    /**
     * Выполняет GET-запрос с указанными ранее параметрами и конвертирует ответ в <b>массив</b> объектов указанного класса
     *
     * @param cl класс объектов массива, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public <T extends MetaEntity> ListEntity<T> list(Class<T> cl) throws IOException, ApiClientException {
        JavaType type = objectMapper.getTypeFactory().constructParametricType(ListEntity.class, cl);
        return objectMapper.readValue(get(), type);
    }

    /**
     * Выполняет GET-запрос с указанными ранее параметрами и конвертирует ответ в <b>список</b> объектов указанного класса
     *
     * @param cl класс объектов списка, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public <T extends MetaEntity> List<T> plainList(Class<T> cl) throws IOException, ApiClientException {
        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, cl);
        return objectMapper.readValue(get(), type);
    }

    /**
     * Выполняет POST-запрос с указанными ранее параметрами
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public String post() throws IOException, ApiClientException {
        HttpPost request = new HttpPost(getFullUrl());
        applyHeaders(request);

        if (body != null) {
            String strBody = objectMapper.writeValueAsString(body);
            logger.debug("Тело запроса        {} {}: {}", request.getMethod(), request.getURI(), strBody);
            StringEntity requestEntity = new StringEntity(strBody, ContentType.APPLICATION_JSON);
            request.setEntity(requestEntity);
        }

        return executeRequest(request);
    }

    /**
     * Выполняет POST-запрос с указанными ранее параметрами и сохраняет ответ в указанный файл
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public File postAndSaveTo(File file) throws IOException, ApiClientException {
        HttpPost request = new HttpPost(getFullUrl());
        applyHeaders(request);

        if (body != null) {
            String strBody = objectMapper.writeValueAsString(body);
            logger.debug("Тело запроса        {} {}: {}", request.getMethod(), request.getURI(), strBody);
            StringEntity requestEntity = new StringEntity(strBody, ContentType.APPLICATION_JSON);
            request.setEntity(requestEntity);
        }

        byte[] data = executeByteRequest(request);
        FileUtils.writeByteArrayToFile(file, data);
        return file;
    }

    /**
     * Выполняет POST-запрос с указанными ранее параметрами и конвертирует ответ в объект указанного класса
     *
     * @param cl класс, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public <T> T post(Class<T> cl) throws IOException, ApiClientException {
        String str = post();
        return objectMapper.readValue(str, cl);
    }

    /**
     * Выполняет POST-запрос с указанными ранее параметрами и конвертирует ответ в <b>массив</b> объектов указанного класса
     *
     * @param cl класс объектов массива, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public <T> List<T> postList(Class<T> cl) throws IOException, ApiClientException {
        JavaType type = objectMapper.getTypeFactory().constructParametricType(List.class, cl);
        String str = post();
        if (str == null || str.isEmpty()) return null;
        return objectMapper.readValue(str, type);
    }

    /**
     * Выполняет DELETE-запрос с указанными ранее параметрами
     *
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public void delete() throws IOException, ApiClientException {
        HttpDelete request = new HttpDelete(getFullUrl());
        applyHeaders(request);
        executeRequest(request);
    }

    /**
     * Выполняет PUT-запрос с указанными ранее параметрами
     *
     * @return тело ответа
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public String put() throws IOException, ApiClientException {
        HttpPut request = new HttpPut(getFullUrl());
        applyHeaders(request);

        if (body != null) {
            String strBody = objectMapper.writeValueAsString(body);
            logger.debug("Тело запроса        {} {}: {}", request.getMethod(), request.getURI(), strBody);
            StringEntity requestEntity = new StringEntity(strBody, ContentType.APPLICATION_JSON);
            request.setEntity(requestEntity);
        }

        return executeRequest(request);
    }

    /**
     * Выполняет PUT-запрос с указанными ранее параметрами и конвертирует ответ в объект указанного класса
     *
     * @param cl класс, в который нужно сконвертировать ответ на запрос
     * @throws IOException        когда возникла сетевая ошибка
     * @throws ApiClientException когда возникла ошибка API
     */
    public <T> T put(Class<? extends T> cl) throws IOException, ApiClientException {
        return objectMapper.readValue(put(), cl);
    }
}
