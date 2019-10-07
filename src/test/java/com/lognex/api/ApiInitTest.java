package com.lognex.api;

import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestConstants;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApiInitTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_emptyAuthData() throws IOException, InterruptedException {
        ApiClient api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, "", ""
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение ApiClientException!");
        } catch (ApiClientException e) {
            assertApiError(
                    e, 401, 1056,
                    // FIXME add " или ключ авторизации" to the end of text after https://lognex.atlassian.net/browse/MC-35268
                    "Ошибка аутентификации: Неправильный пароль или имя пользователя"
            );

            Thread.sleep(1500); // Защита от лимитов
        }
    }

    @Test
    public void test_nullAuthData() throws IOException, InterruptedException {
        ApiClient api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, null, null
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение ApiClientException!");
        } catch (ApiClientException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неверный формат имени пользователя: null"
            );

            Thread.sleep(1500); // Защита от лимитов
        }
    }

    @Test
    public void test_loginFormat() throws IOException, InterruptedException {
        String login = randomString();

        ApiClient api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, login, randomString()
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение ApiClientException!");
        } catch (ApiClientException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неверный формат имени пользователя: " + login
            );

            Thread.sleep(1500); // Защита от лимитов
        }
    }

    @Test
    public void test_correctLoginFormatWrongCredentials() throws IOException, InterruptedException {
        ApiClient api = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, randomString() + "@" + randomString(), randomString()
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение ApiClientException!");
        } catch (ApiClientException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неправильный пароль или имя пользователя"
            );

            Thread.sleep(1500); // Защита от лимитов
        }
    }

    @Test
    public void test_nullHost() {
        try {
            new ApiClient(
                    null,
                    TestConstants.FORCE_HTTPS_FOR_TESTS, randomString() + "@" + randomString(), randomString()
            );
            fail("Ожидалось исключение IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertEquals("Адрес хоста API не может быть пустым или null!", e.getMessage());
        }
    }

    @Test
    public void test_emptyHost() {
        try {
            new ApiClient(
                    "",
                    TestConstants.FORCE_HTTPS_FOR_TESTS, randomString() + "@" + randomString(), randomString()
            );
            fail("Ожидалось исключение IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertEquals("Адрес хоста API не может быть пустым или null!", e.getMessage());
        }
    }
}
