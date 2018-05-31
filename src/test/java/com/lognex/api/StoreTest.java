package com.lognex.api;

import com.google.gson.Gson;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Store;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class StoreTest implements TestRandomizers, TestAsserts {
    private static final Logger logger = LogManager.getLogger(StoreTest.class);
    private Gson gson = LognexApi.createGson(true, true);
    private LognexApi api;

    @Before
    public void init() {
        api = new LognexApi(
                System.getenv("API_HOST"),
                System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        ).timeWithMilliseconds();
    }

    @Test
    public void test_getList() throws IOException, LognexApiException {
        /*
            Запрос
         */

        ListResponse<Store> list = api.entity().store().get();
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(list));

        /*
            Валидация
         */

        assertNotNull(list);
        assertEntityMeta(list.context.employee, Meta.Type.employee, false);
        assertListMeta(list.meta, Meta.Type.store);

        assertNotNull(list.rows);
        assertFalse(list.rows.isEmpty());

        for (MetaEntity row : list.rows) {
            assertNotNull(row.meta);
            assertNotNull(row.meta.href);
            assertEquals(Meta.Type.store, row.meta.type);
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        // TODO
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
