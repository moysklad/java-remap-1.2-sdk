package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

import static org.junit.Assert.*;

public class TokenTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, ApiClientException {
        Token token = api.entity().token().create();
        assertNotNull(token.getAccessToken());
        assertTrue(token.getAccessToken().length() == 40);
    }
}