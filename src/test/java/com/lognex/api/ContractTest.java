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
        assertEntityMeta(list.getContext().getEmployee(), Meta.Type.employee, false);
        assertListMeta(list.getMeta(), Meta.Type.contract);

        assertNotNull(list.getRows());
        assertFalse(list.getRows().isEmpty());

        for (MetaEntity row : list.getRows()) {
            assertNotNull(row.getMeta());
            assertNotNull(row.getMeta().getHref());
            assertEquals(Meta.Type.contract, row.getMeta().getType());
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

        Contract entity = new Contract();

        entity.setName(randomString(5));
        entity.setAgent(counterparty);
        entity.setOwnAgent(organization);

        Contract origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().contract().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.getMeta());
        assertNotNull(entity.getMeta());

        assertEntityMeta(entity.getMeta(), Meta.Type.contract, true);
        assertEquals(origEntity.getName(), entity.getName());

        assertEquals(false, entity.getShared());
        assertEquals(false, entity.getArchived());
        assertEquals(Contract.Type.sales, entity.getContractType());
        assertNotNull(entity.getExternalCode());
        assertNotNull(entity.getUpdated());
        assertNotNull(entity.getMoment());

        assertEntityMeta(entity.getGroup(), Meta.Type.group, false);
        assertEntityMeta(entity.getOwner(), Meta.Type.employee, true);

        assertNotNull(entity.getAgent());
        assertNotNull(entity.getAgent().getMeta());
        assertEquals(counterparty.getMeta(), entity.getAgent().getMeta());

        assertNotNull(entity.getOwnAgent());
        assertNotNull(entity.getOwnAgent().getMeta());
        assertEquals(organization.getMeta(), entity.getOwnAgent().getMeta());
    }

    @Test
    @Ignore
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        // TODO
    }
}
