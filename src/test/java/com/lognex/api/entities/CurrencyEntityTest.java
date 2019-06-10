package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CurrencyEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CurrencyEntity currency = new CurrencyEntity();
        currency.setName("currency_" + randomString(3) + "_" + new Date().getTime());
        currency.setArchived(false);
        currency.setCode(randomString(3));
        currency.setIsoCode(randomString(3));

        CurrencyEntity.Unit major = new CurrencyEntity.Unit();
        major.setGender(CurrencyEntity.Unit.Gender.masculine);
        major.setS1(randomString());
        major.setS2(randomString());
        major.setS5(randomString());
        currency.setMajorUnit(major);

        CurrencyEntity.Unit minor = new CurrencyEntity.Unit();
        minor.setGender(CurrencyEntity.Unit.Gender.feminine);
        minor.setS1(randomString());
        minor.setS2(randomString());
        minor.setS5(randomString());
        currency.setMinorUnit(minor);

        api.entity().currency().post(currency);

        ListEntity<CurrencyEntity> updatedEntitiesList = api.entity().currency().get(filterEq("name", currency.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CurrencyEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(currency.getName(), retrievedEntity.getName());
        assertEquals(currency.getArchived(), retrievedEntity.getArchived());
        assertEquals(currency.getCode(), retrievedEntity.getCode());
        assertEquals(currency.getIsoCode(), retrievedEntity.getIsoCode());
        assertEquals(currency.getMajorUnit(), retrievedEntity.getMajorUnit());
        assertEquals(currency.getMinorUnit(), retrievedEntity.getMinorUnit());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CurrencyEntity currency = simpleEntityFactory.createSimpleCurrency();

        CurrencyEntity retrievedEntity = api.entity().currency().get(currency.getId());
        getAsserts(currency, retrievedEntity);

        retrievedEntity = api.entity().currency().get(currency);
        getAsserts(currency, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        CurrencyEntity currency = simpleEntityFactory.createSimpleCurrency();

        CurrencyEntity retrievedOriginalEntity = api.entity().currency().get(currency.getId());
        String name = "mod_" + randomString(3) + "_" + new Date().getTime();
        currency.setName(name);
        api.entity().currency().put(currency.getId(), currency);
        putAsserts(currency, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(currency);
        name = "mod_" + randomString(3) + "_" + new Date().getTime();
        currency.setName(name);

        api.entity().currency().put(currency);
        putAsserts(currency, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CurrencyEntity currency = simpleEntityFactory.createSimpleCurrency();

        ListEntity<CurrencyEntity> entitiesList = api.entity().currency().get(filterEq("name", currency.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().currency().delete(currency.getId());

        entitiesList = api.entity().currency().get(filterEq("name", currency.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CurrencyEntity currency = simpleEntityFactory.createSimpleCurrency();

        ListEntity<CurrencyEntity> entitiesList = api.entity().currency().get(filterEq("name", currency.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().currency().delete(currency);

        entitiesList = api.entity().currency().get(filterEq("name", currency.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(CurrencyEntity currency, CurrencyEntity retrievedEntity) {
        assertEquals(currency.getName(), retrievedEntity.getName());
        assertEquals(currency.getCode(), retrievedEntity.getCode());
        assertEquals(currency.getIsoCode(), retrievedEntity.getIsoCode());
    }

    private void putAsserts(CurrencyEntity currency, CurrencyEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CurrencyEntity retrievedUpdatedEntity = api.entity().currency().get(currency.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getCode(), retrievedUpdatedEntity.getCode());
        assertEquals(retrievedOriginalEntity.getIsoCode(), retrievedUpdatedEntity.getIsoCode());
    }
}

