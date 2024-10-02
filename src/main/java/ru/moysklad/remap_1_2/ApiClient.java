package ru.moysklad.remap_1_2;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.LocalDateTime;
import lombok.Getter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import ru.moysklad.remap_1_2.clients.EntityClient;
import ru.moysklad.remap_1_2.clients.NotificationClient;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Agent;
import ru.moysklad.remap_1_2.entities.discounts.Discount;
import ru.moysklad.remap_1_2.entities.documents.DocumentEntity;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceInDocumentMarker;
import ru.moysklad.remap_1_2.entities.documents.markers.FinanceOutDocumentMarker;
import ru.moysklad.remap_1_2.entities.notifications.Notification;
import ru.moysklad.remap_1_2.entities.notifications.NotificationExchange;
import ru.moysklad.remap_1_2.entities.notifications.NotificationSubscription;
import ru.moysklad.remap_1_2.entities.products.markers.ConsignmentParentMarker;
import ru.moysklad.remap_1_2.entities.products.markers.ProductAttributeMarker;
import ru.moysklad.remap_1_2.entities.products.markers.ProductMarker;
import ru.moysklad.remap_1_2.entities.products.markers.SingleProductMarker;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.CompanySettingsMetadata;
import ru.moysklad.remap_1_2.utils.NoAuthRedirectStrategy;
import ru.moysklad.remap_1_2.utils.json.*;


@Getter
public final class ApiClient {
    private final String host;
    private String login;
    private String password;
    private String token;
    private CloseableHttpClient client;
    private boolean prettyPrintJson = false;
    private boolean pricePrecision = false;
    private boolean withoutWebhookContent = false;

    /**
     * Создаёт экземпляр коннектора API
     *
     * @param host       хост, на котором располагается API
     * @param forceHttps форсировать запрос через HTTPS
     * @param login      логин пользователя
     * @param password   пароль пользователя
     */
    public ApiClient(String host, boolean forceHttps, String login, String password) {
        this(host, forceHttps, login, password, createHttpClient());
    }

    /**
     * Создаёт экземпляр коннектора API
     *
     * @param host       хост, на котором располагается API
     * @param forceHttps форсировать запрос через HTTPS
     * @param login      логин пользователя
     * @param password   пароль пользователя
     * @param client     HTTP-клиент
     */
    public ApiClient(String host, boolean forceHttps, String login, String password, CloseableHttpClient client) {
        if (host == null || host.trim().isEmpty()) throw new IllegalArgumentException("Адрес хоста API не может быть пустым или null!");
        host = host.trim();

        while (host.endsWith("/")) host = host.substring(0, host.lastIndexOf("/"));

        if (forceHttps) {
            if (host.startsWith("http://")) host = host.replace("http://", "https://");
            else if (!host.startsWith("https://")) host = "https://" + host;
        } else {
            if (!host.startsWith("https://") && !host.startsWith("http://")) host = "http://" + host;
        }

        this.host = host;
        this.client = client;
        setCredentials(login, password);
    }


    public static ApiClient createWithBearerToken(String host, boolean forceHttps, String token, CloseableHttpClient client) {
        ApiClient apiClient = new ApiClient(host, forceHttps, null, null, client);
        apiClient.setToken(token);
        return apiClient;
    }

    public static ApiClient createWithBearerToken(String host, boolean forceHttps, String token) {
        return createWithBearerToken(host, forceHttps, token, createHttpClient());
    }

    private static CloseableHttpClient createHttpClient() {
        return HttpClients.custom()
                .setRedirectStrategy(new NoAuthRedirectStrategy())
                .build();
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
     * Устанавливает Bearer токен авторизации запрсоов к API
     *
     * @param token Bearer токен авторизации
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Устанавливает пользовательский HTTP-клиент, с помощью которого будут выполняться запросы.
     */
    public void setHttpClient(CloseableHttpClient client) {
        this.client = client;
    }

    /**
     * Группа методов API, соответствующих пути <code>/entity/*</code><br>
     * <br>
     * <b>Внимание!</b> Внутри этой цепочки методов каждый сегмент — это отдельный объект. По
     * возможности избегайте их сохранения в переменные в долгоживущих объектах или не забывайте
     * про них, так как неосторожное использование может вызвать <b>утечку памяти</b>!
     */
    public EntityClient entity() {
        return new EntityClient(this);
    }

    /**
     * Группа методов API, соответствующих пути <code>/notification/*</code><br>
     * <br>
     * <b>Внимание!</b> Внутри этой цепочки методов каждый сегмент — это отдельный объект. По
     * возможности избегайте их сохранения в переменные в долгоживущих объектах или не забывайте
     * про них, так как неосторожное использование может вызвать <b>утечку памяти</b>!
     */
    public NotificationClient notification() { return new NotificationClient(this); }

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
        GsonBuilder gb = new GsonBuilder();

        if (prettyPrinting) {
            gb.setPrettyPrinting();
        }

        ProductAttributeMarkerSerializer pams = new ProductAttributeMarkerSerializer();
        gb.registerTypeAdapter(ProductAttributeMarker.class, pams);

        ProductMarkerSerializer pmse = new ProductMarkerSerializer();
        gb.registerTypeAdapter(ProductMarker.class, pmse);
        gb.registerTypeAdapter(SingleProductMarker.class, pmse);
        gb.registerTypeAdapter(ConsignmentParentMarker.class, pmse);
        gb.registerTypeAdapter(Assortment.class, pmse);

        FinanceDocumentMarkerSerializer fdms = new FinanceDocumentMarkerSerializer();
        gb.registerTypeAdapter(FinanceDocumentMarker.class, fdms);
        gb.registerTypeAdapter(FinanceInDocumentMarker.class, fdms);
        gb.registerTypeAdapter(FinanceOutDocumentMarker.class, fdms);

        gb.registerTypeAdapter(DocumentEntity.class, new DocumentEntitySerializer());
        gb.registerTypeAdapter(Agent.class, new AgentDeserializer());
        gb.registerTypeAdapter(Attribute.class, new AttributeSerializer());
        gb.registerTypeAdapter(DocumentAttribute.class, new OperationAttributeSerializer());
        gb.registerTypeAdapter(Currency.MultiplicityType.class, new Currency.MultiplicityType.Serializer());
        gb.registerTypeAdapter(Discount.class, new DiscountDeserializer());
        gb.registerTypeAdapter(ListEntity.class, new ListEntityDeserializer());
        gb.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());
        gb.registerTypeAdapter(CompanySettingsMetadata.CustomEntityMetadata.class, new CustomEntityMetadataDeserializer());
        gb.registerTypeAdapter(Barcode.class, new Barcode.Serializer());
        gb.registerTypeAdapter(DocumentTemplate.class, new DocumentTemplate.Serializer());
        gb.registerTypeAdapter(Meta.Type.class, new Meta.Type.Serializer());
        gb.registerTypeAdapter(Template.class, new Template.Deserializer());
        gb.registerTypeAdapter(Publication.class, new Publication.Deserializer());
        gb.registerTypeAdapter(NotificationExchange.TaskType.class, new EnumSwitchCaseSerializer<NotificationExchange.TaskType>());
        gb.registerTypeAdapter(NotificationExchange.TaskState.class, new EnumSwitchCaseSerializer<NotificationExchange.TaskState>());
        gb.registerTypeAdapter(Notification.class, new NotificationDeserializer());
        gb.registerTypeAdapter(NotificationSubscription.Channel.class, new EnumSwitchCaseSerializer<NotificationSubscription.Channel>());
        gb.registerTypeAdapter(RetailStore.PriorityOfdSend.class, new EnumSwitchCaseSerializer<RetailStore.PriorityOfdSend>());

        return gb.create();
    }

    public ApiClient prettyPrintJson() {
        return prettyPrintJson(true);
    }

    public ApiClient prettyPrintJson(boolean value) {
        this.prettyPrintJson = value;
        return this;
    }

    public ApiClient precision() {
        return precision(true);
    }

    public ApiClient precision(boolean value) {
        this.pricePrecision = value;
        return this;
    }

    public ApiClient withoutWebhookContent() {
        return withoutWebhookContent(true);
    }

    public ApiClient withoutWebhookContent(boolean without) {
        this.withoutWebhookContent = without;
        return this;
    }
}
