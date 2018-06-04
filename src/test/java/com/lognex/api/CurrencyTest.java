package com.lognex.api;

import com.google.gson.Gson;
import com.lognex.api.entities.Currency;
import com.lognex.api.entities.Entity;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListResponse;
import com.lognex.api.utils.LognexApiException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class CurrencyTest implements TestRandomizers, TestAsserts {
    private static final Logger logger = LogManager.getLogger(CurrencyTest.class);
    private Gson gson = LognexApi.createGson(true);
    private LognexApi api;

    @Before
    public void init() {
        api = new LognexApi(
                System.getenv("API_HOST"),
                System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD")
        );
    }

    @Test
    public void test_getList() throws IOException, LognexApiException {
        /*
            Запрос
         */

        ListResponse<Currency> list = api.entity().currency().get();
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(list));

        /*
            Валидация
         */

        assertNotNull(list);
        assertEntityMeta(list.getContext().getEmployee(), Meta.Type.employee, false);
        assertListMeta(list.getMeta(), Meta.Type.contract);

        assertNotNull(list.getRows());
        assertFalse(list.getRows().isEmpty());

        for (MetaEntity row : list.getRows()) {
            assertNotNull(row.getMeta());
            assertNotNull(row.getMeta().getHref());
            assertEquals(Meta.Type.currency, row.getMeta().getType());
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Currency entity = new Currency();

        entity.setName(randomString(5));
        entity.setCode(String.valueOf(100 + rnd.nextInt(900)));
        entity.setIsoCode(randomString(3).toUpperCase());

        Currency origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().currency().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.getMeta());
        assertNotNull(entity.getMeta());

        assertEntityMeta(entity.getMeta(), Meta.Type.currency, true);
        assertEquals(origEntity.getName(), entity.getName());
        assertEquals(origEntity.getCode(), entity.getCode());
        assertEquals(origEntity.getIsoCode(), entity.getIsoCode());

        assertEquals(false, entity.getSystem());
        assertEquals(false, entity.getIndirect());
        assertEquals(false, entity.getArchived());
        assertEquals(false, entity.getDef());

        assertNotNull(entity.getMinorUnit());
        assertNotNull(entity.getMajorUnit());

        assertEquals(Currency.Unit.Gender.masculine, entity.getMajorUnit().getGender());
        assertEquals(Currency.Unit.Gender.masculine, entity.getMajorUnit().getGender());

        assertNull(entity.getFullName());

        assertNotNull(entity.getRate());
        assertNotNull(entity.getMultiplicity());
        assertEquals(1.0, entity.getRate(), 0);
        assertEquals(Currency.MultiplicityType._1, entity.getMultiplicity());
        assertEquals(false, entity.getIndirect());
    }

    @Test
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Currency entity = new Currency();

        entity.setName(randomString(5));
        entity.setFullName(randomString(10));
        entity.setCode(String.valueOf(100 + rnd.nextInt(900)));
        entity.setIsoCode(randomString(3).toUpperCase());
        entity.setIndirect(true);
        entity.setRate(randomDouble(10, 100, 2));
        entity.setMultiplicity(randomEnum(Currency.MultiplicityType.class));
        entity.setRateUpdateType(Currency.UpdateType.auto);
        entity.setSystem(true);
        entity.setArchived(true);
        entity.setDef(true);

        entity.setMinorUnit(new Currency.Unit());
        entity.getMinorUnit().setGender(Currency.Unit.Gender.feminine);
        entity.getMinorUnit().setS1(randomString(3));
        entity.getMinorUnit().setS2(randomString(4));
        entity.getMinorUnit().setS5(randomString(5));

        entity.setMajorUnit(new Currency.Unit());
        entity.getMajorUnit().setGender(Currency.Unit.Gender.feminine);
        entity.getMajorUnit().setS1(randomString(6));
        entity.getMajorUnit().setS2(randomString(7));
        entity.getMajorUnit().setS5(randomString(8));

        Currency origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().currency().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.getMeta());
        assertNotNull(entity.getMeta());

        assertEntityMeta(entity.getMeta(), Meta.Type.currency, true);
        assertEquals(origEntity.getName(), entity.getName());
        assertEquals(origEntity.getFullName(), entity.getFullName());
        assertEquals(origEntity.getCode(), entity.getCode());
        assertEquals(origEntity.getIsoCode(), entity.getIsoCode());
        assertEquals(origEntity.getIndirect(), entity.getIndirect());
        assertEquals(origEntity.getRate(), entity.getRate());
        assertEquals(origEntity.getMultiplicity(), entity.getMultiplicity());
        assertEquals(Currency.UpdateType.manual, entity.getRateUpdateType());

        assertEquals(false, entity.getSystem());
        assertEquals(origEntity.getArchived(), entity.getArchived());
        assertEquals(false, entity.getDef());

        assertEquals(origEntity.getMinorUnit(), entity.getMinorUnit());
        assertEquals(origEntity.getMajorUnit(), entity.getMajorUnit());
    }
}
