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
        assertEntityMeta(list.context.employee, Meta.Type.employee, false);
        assertListMeta(list.meta, Meta.Type.contract);

        assertNotNull(list.rows);
        assertFalse(list.rows.isEmpty());

        for (MetaEntity row : list.rows) {
            assertNotNull(row.meta);
            assertNotNull(row.meta.href);
            assertEquals(Meta.Type.currency, row.meta.type);
        }
    }

    @Test
    public void test_createWithMinimumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Currency entity = new Currency();

        entity.name = randomString(5);
        entity.code = String.valueOf(100 + rnd.nextInt(900));
        entity.isoCode = randomString(3).toUpperCase();

        Currency origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().currency().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.meta);
        assertNotNull(entity.meta);

        assertEntityMeta(entity.meta, Meta.Type.currency, true);
        assertEquals(origEntity.name, entity.name);
        assertEquals(origEntity.code, entity.code);
        assertEquals(origEntity.isoCode, entity.isoCode);

        assertEquals(false, entity.system);
        assertEquals(false, entity.indirect);
        assertEquals(false, entity.archived);
        assertEquals(false, entity.def);

        assertNotNull(entity.minorUnit);
        assertNotNull(entity.majorUnit);

        assertEquals(Currency.Unit.Gender.masculine, entity.minorUnit.gender);
        assertEquals(Currency.Unit.Gender.masculine, entity.majorUnit.gender);

        assertNull(entity.fullName);

        assertNotNull(entity.rate);
        assertNotNull(entity.multiplicity);
        assertEquals(1.0, entity.rate, 0);
        assertEquals(Currency.MultiplicityType._1, entity.multiplicity);
        assertEquals(false, entity.indirect);
    }

    @Test
    public void test_createWithMaximumFields() throws IOException, LognexApiException {
        /*
            Подготовка данных
         */

        Currency entity = new Currency();

        entity.name = randomString(5);
        entity.fullName = randomString(10);
        entity.code = String.valueOf(100 + rnd.nextInt(900));
        entity.isoCode = randomString(3).toUpperCase();
        entity.indirect = true;
        entity.rate = randomDouble(10, 100, 2);
        entity.multiplicity = randomEnum(Currency.MultiplicityType.class);
        entity.rateUpdateType = Currency.UpdateType.auto;
        entity.system = true;
        entity.archived = true;
        entity.def = true;

        entity.minorUnit = new Currency.Unit();
        entity.minorUnit.gender = Currency.Unit.Gender.feminine;
        entity.minorUnit.s1 = randomString(3);
        entity.minorUnit.s2 = randomString(4);
        entity.minorUnit.s5 = randomString(5);

        entity.majorUnit = new Currency.Unit();
        entity.majorUnit.gender = Currency.Unit.Gender.feminine;
        entity.majorUnit.s1 = randomString(6);
        entity.majorUnit.s2 = randomString(7);
        entity.majorUnit.s5 = randomString(8);

        Currency origEntity = Entity.clone(entity);

        /*
            Запрос
         */

        api.entity().currency().post(entity);
        logger.info("Ответ:\n\n{}\n\n", gson.toJson(entity));

        /*
            Валидация
         */

        assertNull(origEntity.meta);
        assertNotNull(entity.meta);

        assertEntityMeta(entity.meta, Meta.Type.currency, true);
        assertEquals(origEntity.name, entity.name);
        assertEquals(origEntity.fullName, entity.fullName);
        assertEquals(origEntity.code, entity.code);
        assertEquals(origEntity.isoCode, entity.isoCode);
        assertEquals(origEntity.indirect, entity.indirect);
        assertEquals(origEntity.rate, entity.rate);
        assertEquals(origEntity.multiplicity, entity.multiplicity);
        assertEquals(Currency.UpdateType.manual, entity.rateUpdateType);

        assertEquals(false, entity.system);
        assertEquals(origEntity.archived, entity.archived);
        assertEquals(false, entity.def);

        assertEquals(origEntity.minorUnit.gender, entity.minorUnit.gender);
        assertEquals(origEntity.minorUnit.s1, entity.minorUnit.s1);
        assertEquals(origEntity.minorUnit.s2, entity.minorUnit.s2);
        assertEquals(origEntity.minorUnit.s5, entity.minorUnit.s5);

        assertEquals(origEntity.majorUnit.gender, entity.majorUnit.gender);
        assertEquals(origEntity.majorUnit.s1, entity.majorUnit.s1);
        assertEquals(origEntity.majorUnit.s2, entity.majorUnit.s2);
        assertEquals(origEntity.majorUnit.s5, entity.majorUnit.s5);
    }
}
