package com.lognex.api;


import com.lognex.api.request.builder.entity.EntityRequestBuilder;
import com.lognex.api.request.builder.entity.EntityRequestBuilderImpl;
import lombok.Getter;

@Getter
public class ApiClient {

    String login;
    String password;

    public ApiClient(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public EntityRequestBuilder entity(String type) {
        return new EntityRequestBuilderImpl(type, this);
    }

    public void report() {
    }

    public void audit() {
    }


    public void context() {
    }

}
