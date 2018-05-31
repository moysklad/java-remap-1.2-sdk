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

public class ContractTest implements TestRandomizers, TestAsserts {
    private static final Logger logger = LogManager.getLogger(ContractTest.class);
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

        ListResponse<Contract> list = api.entity().contract().get();
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(list));

        /*
            Запрос
         */

        assertNotNull(list);
        assertEntityMeta(list.context.employee, Meta.Type.employee, false);
        assertListMeta(list.meta, Meta.Type.contract);

        assertNotNull(list.rows);
        assertFalse(list.rows.isEmpty());

        for (MetaEntity row : list.rows) {
            assertNotNull(row.meta);
            assertNotNull(row.meta.href);
            assertEquals(Meta.Type.contract, row.meta.type);
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Counterparty counterparty = new Counterparty();
        counterparty.name = randomString(5);
        api.entity().counterparty().post(counterparty);

        Organization organization = new Organization();
        organization.name = randomString(5);
        api.entity().organization().post(organization);

        Contract entity = new Contract();

        entity.name = randomString(5);
        entity.agent = counterparty;
        entity.ownAgent = organization;

        Contract origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().contract().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.meta);
        assertNotNull(entity.meta);

        assertEntityMeta(entity.meta, Meta.Type.contract, true);
        assertEquals(origEntity.name, entity.name);

        assertEquals(false, entity.shared);
        assertEquals(false, entity.archived);
        assertEquals(Contract.Type.sales, entity.contractType);
        assertNotNull(entity.externalCode);
        assertNotNull(entity.updated);
        assertNotNull(entity.moment);

        assertEntityMeta(entity.group, Meta.Type.group, false);
        assertEntityMeta(entity.owner, Meta.Type.employee, true);

        assertNotNull(entity.agent);
        assertNotNull(entity.agent.meta);
        assertEquals(counterparty.meta, entity.agent.meta);

        assertNotNull(entity.ownAgent);
        assertNotNull(entity.ownAgent.meta);
        assertEquals(organization.meta, entity.ownAgent.meta);
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
