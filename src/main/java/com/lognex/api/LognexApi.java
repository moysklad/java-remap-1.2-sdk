package com.lognex.api;

import com.lognex.api.builders.entities.EntityRequestBuilder;
import lombok.Getter;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

@Getter
public final class LognexApi {
    public static final String API_PATH = "/api/remap/1.1";
    private final String host;
    private final String login;
    private final String password;
    private CloseableHttpClient client;

    /**
     * Создаёт экземпляр коннектора API
     *
     * @param host     хост, на котором располагается API
     * @param login    логин пользователя
     * @param password пароль пользователя
     */
    public LognexApi(String host, String login, String password) {
        while (host.endsWith("/")) host = host.substring(0, host.lastIndexOf("/"));

        this.login = login;
        this.host = host;
        this.password = password;
        this.client = HttpClients.createDefault();
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
    public EntityRequestBuilder entity() {
        return new EntityRequestBuilder(this);
    }
}
