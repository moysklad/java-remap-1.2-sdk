package com.lognex.api;


import com.lognex.api.request.builder.entity.EntityRequestBuilder;
import com.lognex.api.request.builder.entity.EntityRequestBuilderImpl;
import com.lognex.api.util.Constants;
import com.lognex.api.util.Type;
import lombok.Getter;

public class ApiClient {

    @Getter
    String login;
    @Getter
    String password;
    private String host;

    public ApiClient(String login, String password, String host) {
        this.login = login;
        this.password = password;
        String apiResourceSuffix = "/api/remap/1.1";
        this.host = host != null && !host.isEmpty() ?
                host + apiResourceSuffix :
                Constants.DEFAULT_HOST_URL + apiResourceSuffix;
    }

    public EntityRequestBuilder entity(Type type) {
        return new EntityRequestBuilderImpl(host, type, this);
    }

    public void report() {
    }

    public void audit() {
    }


    public void context() {
    }

}
