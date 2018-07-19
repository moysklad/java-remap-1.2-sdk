package com.lognex.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lognex.api.clients.EntityClient;
import com.lognex.api.entities.CurrencyEntity;
import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.products.markers.ConsignmentParentMarker;
import com.lognex.api.entities.products.markers.ProductMarker;
import com.lognex.api.entities.products.markers.SingleProductMarker;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.json.AgentDeserializer;
import com.lognex.api.utils.json.ListEntityDeserializer;
import com.lognex.api.utils.json.LocalDateTimeSerializer;
import com.lognex.api.utils.json.ProductMarkerSerializer;
import lombok.Getter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.time.LocalDateTime;

@Getter
public final class LognexApi {
    public static final String API_PATH = "/api/remap/1.1";
    private final String host;
    private String login;
    private String password;
    private CloseableHttpClient client;
    private boolean timeWithMilliseconds = false;

    /**
     * Создаёт экземпляр коннектора API
     *
     * @param host     хост, на котором располагается API
     * @param login    логин пользователя
     * @param password пароль пользователя
     */
    public LognexApi(String host, String login, String password) {
        if (host == null || host.trim().isEmpty()) throw new IllegalArgumentException("Адрес хоста API не может быть пустым или null!");
        host = host.trim();

        while (host.endsWith("/")) host = host.substring(0, host.lastIndexOf("/"));
        if (host.startsWith("http://")) host = host.replace("http://", "https://");
        else if (!host.startsWith("https://")) host = "https://" + host;

        this.host = host;
        this.client = HttpClients.createDefault();
        setCredentials(login, password);
    }

    /**
     * Устанавливает данные доступа, которые используются для авторизации
     * запросов к API
     *
     * @param login    логин в формате <code>[имя_пользователя]@[название_компании]</code>
     * @param password пароль
     */
    public void setCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    /**
     * Устанавливает пользовательский HTTP-клиент, с помощью которого будут выполняться запросы.
     */
    public void setHttpClient(CloseableHttpClient client) {
        this.client = client;
    }

    /**
     * Группа методов API, соответствующих пути <code>/entity/*</code><br/>
     * <br/>
     * <b>Внимание!</b> Внутри этой цепочки методов каждый сегмент — это отдельный объект. По
     * возможности избегайте их сохранения в переменные в долгоживущих объектах или не забывайте
     * про них, так как неосторожное использование может вызвать <b>утечку памяти</b>!<br/><br/>
     */
    public EntityClient entity() {
        return new EntityClient(this);
    }

    /**
     * Создаёт экземпляр GSON с настроенными сериализаторами и десериализаторами для
     * некоторых классов и сущностей
     */
    public static Gson createGson() {
        return createGson(false);
    }

    /**
     * Создаёт экземпляр GSON с настроенными сериализаторами и десериализаторами для
     * некоторых классов и сущностей (с возможностью настроить форматированный
     * вывод)
     */
    public static Gson createGson(boolean prettyPrinting) {
        return createGson(prettyPrinting, false);
    }

    /**
     * Создаёт экземпляр GSON с настроенными сериализаторами и десериализаторами для
     * некоторых классов и сущностей (с возможностью настроить форматированный
     * вывод и даты с миллисекундами)
     */
    public static Gson createGson(boolean prettyPrinting, boolean timeWithMilliseconds) {
        GsonBuilder gb = new GsonBuilder();

        if (prettyPrinting) {
            gb.setPrettyPrinting();
        }

        gb.registerTypeAdapter(ProductMarker.class, new ProductMarkerSerializer());
        gb.registerTypeAdapter(SingleProductMarker.class, new ProductMarkerSerializer());
        gb.registerTypeAdapter(ConsignmentParentMarker.class, new ProductMarkerSerializer());
        gb.registerTypeAdapter(ListEntity.class, new ListEntityDeserializer());
        gb.registerTypeAdapter(CurrencyEntity.MultiplicityType.class, new CurrencyEntity.MultiplicityType.Serializer());
        gb.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer(timeWithMilliseconds));
        gb.registerTypeAdapter(AgentEntity.class, new AgentDeserializer());

        return gb.create();
    }

    public LognexApi timeWithMilliseconds() {
        return timeWithMilliseconds(true);
    }

    public LognexApi timeWithMilliseconds(boolean value) {
        this.timeWithMilliseconds = value;
        return this;
    }
}
