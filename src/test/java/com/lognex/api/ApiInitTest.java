package com.lognex.api;

import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ApiInitTest implements TestAsserts, TestRandomizers {
    @Test
    public void test_emptyAuthData() throws IOException, InterruptedException {
        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                true, "", ""
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неправильный пароль или имя пользователя"
            );

            Thread.sleep(1500); // Защита от лимитов
        }
    }

    @Test
    public void test_nullAuthData() throws IOException, InterruptedException {
        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                true, null, null
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
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

        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                true, login, randomString()
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
            assertApiError(
                    e, 401, 1056,
                    "Ошибка аутентификации: Неверный формат имени пользователя: " + login
            );

            Thread.sleep(1500); // Защита от лимитов
        }
    }

    @Test
    public void test_correctLoginFormatWrongCredentials() throws IOException, InterruptedException {
        LognexApi api = new LognexApi(
                System.getenv("API_HOST"),
                true, randomString() + "@" + randomString(), randomString()
        );

        try {
            api.entity().counterparty().get();
            fail("Ожидалось исключение LognexApiException!");
        } catch (LognexApiException e) {
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
            new LognexApi(
                    null,
                    true, randomString() + "@" + randomString(), randomString()
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
                    true, randomString() + "@" + randomString(), randomString()
            );
            fail("Ожидалось исключение IllegalArgumentException!");
        } catch (IllegalArgumentException e) {
            assertEquals("Адрес хоста API не может быть пустым или null!", e.getMessage());
        }
    }
}
