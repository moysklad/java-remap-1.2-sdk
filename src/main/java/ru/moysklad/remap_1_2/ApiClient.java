package ru.moysklad.remap_1_2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
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

import java.time.LocalDateTime;
import java.util.Optional;


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
     * Создаёт экземпляр ObjectMapper с настроенными сериализаторами и десериализаторами для
     * некоторых классов и сущностей
     */
    public static ObjectMapper createObjectMapper() {
        return createObjectMapper(false);
    }

    public static ObjectMapper createObjectMapper(boolean prettyPrinting) {
        ObjectMapper objectMapper = new ObjectMapper();
        if (prettyPrinting) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        objectMapper.configure(com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        SimpleModule module = new SimpleModule();

        module.addDeserializer(ProductAttributeMarker.class, new ProductAttributeMarkerDeserializer());
        module.addDeserializer(Assortment.class, new AssortmentDeserializer());
        module.addDeserializer(ProductMarker.class, new ProductMarkerDeserializer());
        module.addDeserializer(SingleProductMarker.class, new SingleProductMarkerDeserializer());
        module.addDeserializer(ConsignmentParentMarker.class, new ConsignmentParentMarkerDeserializer());
        module.addDeserializer(FinanceDocumentMarker.class, new FinanceDocumentMarkerDeserializer());
        module.addDeserializer(FinanceOutDocumentMarker.class, new FinanceOutDocumentMarkerDeserializer());
        module.addDeserializer(FinanceInDocumentMarker.class, new FinanceInDocumentMarkerDeserializer());
        module.addDeserializer(DocumentEntity.class, new DocumentEntityDeserializer());
        module.addDeserializer(Agent.class, new AgentDeserializer());
        module.addSerializer(Attribute.class, new AttributeSerializer());
        module.addDeserializer(Attribute.class, new AttributeDeserializer());
        module.addSerializer(DocumentAttribute.class, new DocumentAttributeSerializer());
        module.addDeserializer(DocumentAttribute.class, new DocumentAttributeDeserializer());
        module.addSerializer(Currency.MultiplicityType.class, new Currency.MultiplicityType.Serializer());
        module.addDeserializer(Currency.MultiplicityType.class, new Currency.MultiplicityType.Deserializer());
        module.addDeserializer(Discount.class, new DiscountDeserializer());
        module.addDeserializer(ListEntity.class, new ListEntityDeserializer());
        module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer());
        module.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer());
        module.addDeserializer(CompanySettingsMetadata.CustomEntityMetadata.class, new CustomEntityMetadataDeserializer());
        module.addSerializer(Barcode.class, new Barcode.Serializer());
        module.addDeserializer(Barcode.class, new Barcode.Deserializer());
        module.addSerializer(DocumentTemplate.class, new DocumentTemplate.Serializer());
        module.addSerializer(Meta.Type.class, new Meta.Type.Serializer());
        module.addDeserializer(Meta.Type.class, new Meta.Type.Deserializer());
        module.addDeserializer(Template.class, new Template.Deserializer());
        module.addDeserializer(Publication.class, new Publication.Deserializer());
        module.addSerializer(NotificationExchange.TaskType.class, new EnumSwitchCaseSerializer<>(NotificationExchange.TaskType.class));
        module.addDeserializer(NotificationExchange.TaskType.class, new EnumSwitchCaseDeserializer<>(NotificationExchange.TaskType.class));
        module.addSerializer(NotificationExchange.TaskState.class, new EnumSwitchCaseSerializer<>(NotificationExchange.TaskState.class));
        module.addDeserializer(NotificationExchange.TaskState.class, new EnumSwitchCaseDeserializer<>(NotificationExchange.TaskState.class));
        module.addDeserializer(Notification.class, new NotificationDeserializer());
        module.addSerializer(NotificationSubscription.Channel.class, new EnumSwitchCaseSerializer<>(NotificationSubscription.Channel.class));
        module.addDeserializer(NotificationSubscription.Channel.class, new EnumSwitchCaseDeserializer<>(NotificationSubscription.Channel.class));
        module.addSerializer(RetailStore.PriorityOfdSend.class, new EnumSwitchCaseSerializer<>(RetailStore.PriorityOfdSend.class));
        module.addDeserializer(RetailStore.PriorityOfdSend.class, new EnumSwitchCaseDeserializer<>(RetailStore.PriorityOfdSend.class));
        module.addSerializer(Optional.class, new OptionalEmptyAsNullSerializer());

        objectMapper.registerModule(module);

        return objectMapper;
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
