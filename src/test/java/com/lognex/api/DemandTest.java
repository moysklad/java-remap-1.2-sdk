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
        assertEntityMeta(list.getContext().getEmployee(), Meta.Type.employee, false);
        assertListMeta(list.getMeta(), Meta.Type.demand);

        assertNotNull(list.getRows());
        assertFalse(list.getRows().isEmpty());

        for (MetaEntity row : list.getRows()) {
            assertNotNull(row.getMeta());
            assertNotNull(row.getMeta().getHref());
            assertEquals(Meta.Type.demand, row.getMeta().getType());
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Counterparty counterparty = new Counterparty();
        counterparty.setName(randomString(5));
        api.entity().counterparty().post(counterparty);

        Organization organization = new Organization();
        organization.setName(randomString(5));
        api.entity().organization().post(organization);

        Store store = api.entity().store().get().getRows().stream().
                filter(row -> row.getName().equals("Основной склад")).
                findFirst().
                get();

        Demand entity = new Demand();
        entity.setName(randomString(5));
        entity.setAgent(counterparty);
        entity.setOrganization(organization);
        entity.setStore(store);

        Demand origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().demand().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.getMeta());
        assertNotNull(entity.getMeta());

        assertEntityMeta(entity.getMeta(), Meta.Type.demand, true);
        assertEquals(origEntity.getName(), entity.getName());

        assertEquals(false, entity.getShared());
        assertEquals(true, entity.getApplicable());
        assertEquals(0, (int) entity.getVersion());
        assertEquals(0, (int) entity.getSum());
        assertEquals(0, (int) entity.getVatSum());
        assertEquals(0, (int) entity.getPayedSum());
        assertNotNull(entity.getExternalCode());
        assertNotNull(entity.getUpdated());
        assertNotNull(entity.getCreated());

        assertEntityMeta(entity.getGroup(), Meta.Type.group, false);
        assertEntityMeta(entity.getStore(), Meta.Type.store, true);
        assertEntityMeta(entity.getAgent(), Meta.Type.counterparty, true);
        assertEntityMeta(entity.getOrganization(), Meta.Type.organization, true);
        assertEntityMeta(entity.getDocuments(), null, false);
        assertListMeta(entity.getPositions(), Meta.Type.demandposition);
    }

    @Test
    public void test_createWithAgentOrganization() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Organization organization1 = new Organization();
        organization1.setName(randomString(5));
        api.entity().organization().post(organization1);

        Organization organization2 = new Organization();
        organization2.setName(randomString(5));
        api.entity().organization().post(organization2);

        Store store = api.entity().store().get().getRows().stream().
                filter(row -> row.getName().equals("Основной склад")).
                findFirst().
                get();

        Demand entity = new Demand();
        entity.setName(randomString(5));
        entity.setAgent(organization1);
        entity.setOrganization(organization2);
        entity.setStore(store);

        Demand origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().demand().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.getMeta());
        assertNotNull(entity.getMeta());

        assertNotNull(entity.getAgent());
        assertTrue(entity.getAgent() instanceof Organization);
        assertNotNull(entity.getAgent().getMeta());
        assertEquals(organization1.getMeta().getHref(), entity.getAgent().getMeta().getHref());
        assertEquals(Meta.Type.organization, entity.getAgent().getMeta().getType());

        assertNotNull(entity.getOrganization());
        assertNotNull(entity.getOrganization().getMeta());
        assertEquals(organization2.getMeta().getHref(), entity.getOrganization().getMeta().getHref());
        assertEquals(Meta.Type.organization, entity.getOrganization().getMeta().getType());
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
