package com.lognex.api;

import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApiInitTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_emptyAuthData() throws IOException {
        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                "", ""
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неправильный пароль или имя пользователя"
            );
        }
    }

    @Test
    public void test_nullAuthData() throws IOException {
        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                null, null
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неверный формат имени пользователя: null"
            );
        }
    }

    @Test
    public void test_loginFormat() throws IOException {
        String login = randomString();

        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                login, randomString()
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неверный формат имени пользователя: " + login
            );
        }
    }

    @Test
    public void test_correctLoginFormatWrongCredentials() throws IOException {
        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                randomString() + "@" + randomString(), randomString()
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неправильный пароль или имя пользователя"
            );
        }
    }

    @Test
    public void test_nullHost() {
        try {
            new LognexApi(
                    null,
                    randomString() + "@" + randomString(), randomString()
            );
            fail("Ожидалось исключение IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertEquals("Адрес хоста API не может быть пустым или null!", e.getMessage());
        }
    }

    @Test
    public void test_emptyHost() {
        try {
            new LognexApi(
                    "",
                    randomString() + "@" + randomString(), randomString()
            );
            fail("Ожидалось исключение IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertEquals("Адрес хоста API не может быть пустым или null!", e.getMessage());
        }
    }
}
