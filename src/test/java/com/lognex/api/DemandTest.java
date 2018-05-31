package com.lognex.api;

import com.google.gson.Gson;
import com.lognex.api.entities.*;
import com.lognex.api.entities.documents.Demand;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class DemandTest implements TestRandomizers, TestAsserts {
    private static final Logger logger = LogManager.getLogger(DemandTest.class);
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

        ListResponse<Demand> list = api.entity().demand().get();
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(list));

        /*
            Валидация
         */

        assertNotNull(list);
        assertEntityMeta(list.context.employee, Meta.Type.employee, false);
        assertListMeta(list.meta, Meta.Type.demand);

        assertNotNull(list.rows);
        assertFalse(list.rows.isEmpty());

        for (MetaEntity row : list.rows) {
            assertNotNull(row.meta);
            assertNotNull(row.meta.href);
            assertEquals(Meta.Type.demand, row.meta.type);
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

        Store store = api.entity().store().get().rows.stream().filter(row -> row.name.equals("Основной склад")).findFirst().get();

        Demand entity = new Demand();
        entity.name = randomString(5);
        entity.agent = counterparty;
        entity.organization = organization;
        entity.store = store;

        Demand origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().demand().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.meta);
        assertNotNull(entity.meta);

        assertEntityMeta(entity.meta, Meta.Type.demand, true);
        assertEquals(origEntity.name, entity.name);

        assertEquals(false, entity.shared);
        assertEquals(true, entity.applicable);
        assertEquals(0, (int) entity.version);
        assertEquals(0, (int) entity.sum);
        assertEquals(0, (int) entity.vatSum);
        assertEquals(0, (int) entity.payedSum);
        assertNotNull(entity.externalCode);
        assertNotNull(entity.updated);
        assertNotNull(entity.created);

        assertEntityMeta(entity.group, Meta.Type.group, false);
        assertEntityMeta(entity.store, Meta.Type.store, true);
        assertEntityMeta(entity.agent, Meta.Type.counterparty, true);
        assertEntityMeta(entity.organization, Meta.Type.organization, true);
        assertEntityMeta(entity.documents, null, false);
        assertListMeta(entity.positions, Meta.Type.demandposition);
    }

    @Test
    public void test_createWithAgentOrganization() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Organization organization1 = new Organization();
        organization1.name = randomString(5);
        api.entity().organization().post(organization1);

        Organization organization2 = new Organization();
        organization2.name = randomString(5);
        api.entity().organization().post(organization2);

        Store store = api.entity().store().get().rows.stream().filter(row -> row.name.equals("Основной склад")).findFirst().get();

        Demand entity = new Demand();
        entity.name = randomString(5);
        entity.agent = organization1;
        entity.organization = organization2;
        entity.store = store;

        Demand origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().demand().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.meta);
        assertNotNull(entity.meta);

        assertNotNull(entity.agent);
        assertTrue(entity.agent instanceof Organization);
        assertNotNull(entity.agent.meta);
        assertEquals(organization1.meta.href, entity.agent.meta.href);
        assertEquals(Meta.Type.organization, entity.agent.meta.type);

        assertNotNull(entity.organization);
        assertNotNull(entity.organization.meta);
        assertEquals(organization2.meta.href, entity.organization.meta.href);
        assertEquals(Meta.Type.organization, entity.organization.meta.type);
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
