package com.lognex.api;

import com.google.gson.Gson;
import com.lognex.api.entities.*;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CounterpartyTest implements TestRandomizers, TestAsserts {
    private static final Logger logger = LogManager.getLogger(CounterpartyTest.class);
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

        ListResponse<Counterparty> list = api.entity().counterparty().get();
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(list));

        /*
            Валидация
         */

        assertNotNull(list);
        assertEntityMeta(list.context.employee, Meta.Type.employee, false);
        assertListMeta(list.meta, Meta.Type.counterparty);

        assertNotNull(list.rows);
        assertFalse(list.rows.isEmpty());

        for (MetaEntity row : list.rows) {
            assertNotNull(row.meta);
            assertNotNull(row.meta.href);
            assertEquals(Meta.Type.counterparty, row.meta.type);
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Counterparty entity = new Counterparty();
        entity.name = randomString(5);
        Counterparty origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().counterparty().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.meta);
        assertNotNull(entity.meta);

        assertEntityMeta(entity.meta, Meta.Type.counterparty, true);
        assertEquals(origEntity.name, entity.name);

        assertEquals(origEntity.name, entity.name);

        assertEquals(false, entity.shared);
        assertEquals(false, entity.archived);
        assertEquals(0, (int) entity.version);
        assertEquals(0, (int) entity.salesAmount);
        assertEquals(CompanyType.legal, entity.companyType);
        assertNotNull(entity.externalCode);
        assertNotNull(entity.updated);
        assertNotNull(entity.created);

        assertListMeta(entity.accounts, Meta.Type.account);
        assertListMeta(entity.notes, Meta.Type.note);
        assertEntityMeta(entity.state, Meta.Type.state, false);

        assertNotNull(entity.tags);
        assertTrue(entity.tags.isEmpty());
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
