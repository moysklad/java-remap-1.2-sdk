package com.lognex.api;

import com.lognex.api.builders.EntityRequestBuilder;
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

    public LognexApi(String host, String login, String password) {
        while (host.endsWith("/")) host = host.substring(0, host.lastIndexOf("/"));

        this.login = login;
        this.host = host;
        this.password = password;
        this.client = HttpClients.createDefault();
    }

    public void setHttpClient(CloseableHttpClient client) {
        this.client = client;
    }

    public EntityRequestBuilder entity() {
        return new EntityRequestBuilder(this);
    }
}
