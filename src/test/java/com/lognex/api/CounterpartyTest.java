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
        assertEntityMeta(list.getContext().getEmployee(), Meta.Type.employee, false);
        assertListMeta(list.getMeta(), Meta.Type.counterparty);

        assertNotNull(list.getRows());
        assertFalse(list.getRows().isEmpty());

        for (MetaEntity row : list.getRows()) {
            assertNotNull(row.getMeta());
            assertNotNull(row.getMeta().getHref());
            assertEquals(Meta.Type.counterparty, row.getMeta().getType());
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Counterparty entity = new Counterparty();
        entity.setName(randomString(5));
        Counterparty origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().counterparty().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.getMeta());
        assertNotNull(entity.getMeta());

        assertEntityMeta(entity.getMeta(), Meta.Type.counterparty, true);
        assertEquals(origEntity.getName(), entity.getName());

        assertEquals(origEntity.getName(), entity.getName());

        assertEquals(false, entity.getShared());
        assertEquals(false, entity.getArchived());
        assertEquals(0, (int) entity.getVersion());
        assertEquals(0, (int) entity.getSalesAmount());
        assertEquals(CompanyType.legal, entity.getCompanyType());
        assertNotNull(entity.getExternalCode());
        assertNotNull(entity.getUpdated());
        assertNotNull(entity.getCreated());

        assertListMeta(entity.getAccounts(), Meta.Type.account);
        assertListMeta(entity.getNotes(), Meta.Type.note);
        assertEntityMeta(entity.getState(), Meta.Type.state, false);

        assertNotNull(entity.getTags());
        assertTrue(entity.getTags().isEmpty());
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
