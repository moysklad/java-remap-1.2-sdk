package com.lognex.api.entities;

import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class TokenTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, ApiClientException {
        Token token = api.entity().token().create();
        assertNotNull(token.getAccess_token());
        assertTrue(token.getAccess_token().length() == 40);
    }
}