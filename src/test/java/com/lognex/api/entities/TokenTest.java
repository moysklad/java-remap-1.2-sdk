package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.UUID;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TokenTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, ApiClientException {
        Token token = api.entity().token().createToken();
        assertTrue(token.getAccess_token().length() == 40);
    }

}