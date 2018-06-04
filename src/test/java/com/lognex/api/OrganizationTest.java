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

        assertNotNull(list.getContext());
        assertNotNull(list.getContext().getEmployee());
        assertNotNull(list.getContext().getEmployee().getMeta());
        assertEquals(Meta.Type.employee, list.getContext().getEmployee().getMeta().getType());

        assertNotNull(list.getMeta());
        assertNotNull(list.getMeta().getSize());
        assertNotNull(list.getMeta().getLimit());
        assertNotNull(list.getMeta().getOffset());
        assertEquals(Meta.Type.organization, list.getMeta().getType());

        assertNotNull(list.getRows());
        assertFalse(list.getRows().isEmpty());

        for (Organization row : list.getRows()) {
            assertNotNull(row.getMeta());
            assertNotNull(row.getMeta().getHref());
            assertEquals(Meta.Type.organization, row.getMeta().getType());
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Organization entity = new Organization();

        entity.setName(randomString(5));

        Organization origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().organization().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.getMeta());
        assertNotNull(entity.getMeta());

        assertEntityMeta(entity.getMeta(), Meta.Type.organization, true);
        assertEquals(origEntity.getName(), entity.getName());

        assertNotNull(entity.getId());
        assertNotNull(entity.getAccountId());
        assertEquals(true, entity.getShared());
        assertEquals(true, entity.getPayerVat());
        assertEquals(false, entity.getArchived());
        assertEquals(false, entity.getIsEgaisEnable());
        assertEquals(0, (int) entity.getVersion());
        assertEquals(CompanyType.legal, entity.getCompanyType());
        assertNotNull(entity.getExternalCode());
        assertNotNull(entity.getUpdated());
        assertNotNull(entity.getCreated());

        assertEntityMeta(entity.getAccounts(), Meta.Type.account, false);
        assertEntityMeta(entity.getGroup(), Meta.Type.group, false);
        assertEntityMeta(entity.getOwner(), Meta.Type.employee, false);
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
