package com.lognex.api;

import com.google.gson.Gson;
import com.lognex.api.entities.CompanyType;
import com.lognex.api.entities.Entity;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.Organization;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class OrganizationTest implements TestRandomizers, TestAsserts {
    private static final Logger logger = LogManager.getLogger(OrganizationTest.class);
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

        ListResponse<Organization> list = api.entity().organization().get();
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(list));

        /*
            Валидация
         */

        assertNotNull(list);

        assertNotNull(list.context);
        assertNotNull(list.context.employee);
        assertNotNull(list.context.employee.meta);
        assertEquals(Meta.Type.employee, list.context.employee.meta.type);

        assertNotNull(list.meta);
        assertNotNull(list.meta.size);
        assertNotNull(list.meta.limit);
        assertNotNull(list.meta.offset);
        assertEquals(Meta.Type.organization, list.meta.type);

        assertNotNull(list.rows);
        assertFalse(list.rows.isEmpty());

        for (Organization row : list.rows) {
            assertNotNull(row.meta);
            assertNotNull(row.meta.href);
            assertEquals(Meta.Type.organization, row.meta.type);
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Organization entity = new Organization();

        entity.name = randomString(5);

        Organization origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().organization().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.meta);
        assertNotNull(entity.meta);

        assertEntityMeta(entity.meta, Meta.Type.organization, true);
        assertEquals(origEntity.name, entity.name);

        assertNotNull(entity.id);
        assertNotNull(entity.accountId);
        assertEquals(true, entity.shared);
        assertEquals(true, entity.payerVat);
        assertEquals(false, entity.archived);
        assertEquals(false, entity.isEgaisEnable);
        assertEquals(0, (int) entity.version);
        assertEquals(CompanyType.legal, entity.companyType);
        assertNotNull(entity.externalCode);
        assertNotNull(entity.updated);
        assertNotNull(entity.created);

        assertEntityMeta(entity.accounts, Meta.Type.account, false);
        assertEntityMeta(entity.group, Meta.Type.group, false);
        assertEntityMeta(entity.owner, Meta.Type.employee, false);
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
