package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.ApiEndpoint;
import com.lognex.api.entities.Token;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
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
