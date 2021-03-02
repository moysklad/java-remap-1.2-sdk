package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.ApiEndpoint;
import ru.moysklad.remap_1_2.entities.Token;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public class TokenClient extends EntityClientBase {

    public TokenClient(ApiClient api) {
        super(api, "/security/token");
    }

    @ApiEndpoint
    public Token create() throws IOException, ApiClientException {
        Token responseEntity = HttpRequestExecutor.
                path(api(), path()).
                body(null).
                post(Token.class);
        return responseEntity;
    }


}
