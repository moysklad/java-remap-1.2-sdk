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
        CurrencyEntity e = new CurrencyEntity();
        e.setName("currency_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setCode(randomString(3));
        e.setIsoCode(randomString(3));

        CurrencyEntity.Unit major = new CurrencyEntity.Unit();
        major.setGender(CurrencyEntity.Unit.Gender.masculine);
        major.setS1(randomString());
        major.setS2(randomString());
        major.setS5(randomString());
        e.setMajorUnit(major);

        CurrencyEntity.Unit minor = new CurrencyEntity.Unit();
        minor.setGender(CurrencyEntity.Unit.Gender.feminine);
        minor.setS1(randomString());
        minor.setS2(randomString());
        minor.setS5(randomString());
        e.setMinorUnit(minor);

        api.entity().currency().post(e);

        ListEntity<CurrencyEntity> updatedEntitiesList = api.entity().currency().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        CurrencyEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getIsoCode(), retrievedEntity.getIsoCode());
        assertEquals(e.getMajorUnit(), retrievedEntity.getMajorUnit());
        assertEquals(e.getMinorUnit(), retrievedEntity.getMinorUnit());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        CurrencyEntity e = createSimpleCurrency();

        CurrencyEntity retrievedEntity = api.entity().currency().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().currency().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        CurrencyEntity e = createSimpleCurrency();

        CurrencyEntity retrievedOriginalEntity = api.entity().currency().get(e.getId());
        String name = "mod_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        Thread.sleep(500);
        api.entity().currency().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);
        name = "mod_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);

        Thread.sleep(500);
        api.entity().currency().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        CurrencyEntity e = createSimpleCurrency();

        ListEntity<CurrencyEntity> entitiesList = api.entity().currency().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().currency().delete(e.getId());

        entitiesList = api.entity().currency().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CurrencyEntity e = createSimpleCurrency();

        ListEntity<CurrencyEntity> entitiesList = api.entity().currency().get(filterEq("name", e.getName()));
        assertEquals(1, entitiesList.getRows().size());

        api.entity().currency().delete(e);

        entitiesList = api.entity().currency().get(filterEq("name", e.getName()));
        assertEquals(0, entitiesList.getRows().size());
    }

    private CurrencyEntity createSimpleCurrency() throws IOException, LognexApiException {
        CurrencyEntity e = new CurrencyEntity();
        e.setName("currency_" + randomString(3) + "_" + new Date().getTime());
        e.setArchived(false);
        e.setCode(randomString(3));
        e.setIsoCode(randomString(3));

        CurrencyEntity.Unit major = new CurrencyEntity.Unit();
        major.setGender(CurrencyEntity.Unit.Gender.masculine);
        major.setS1(randomString());
        major.setS2(randomString());
        major.setS5(randomString());
        e.setMajorUnit(major);

        CurrencyEntity.Unit minor = new CurrencyEntity.Unit();
        minor.setGender(CurrencyEntity.Unit.Gender.feminine);
        minor.setS1(randomString());
        minor.setS2(randomString());
        minor.setS5(randomString());
        e.setMinorUnit(minor);

        api.entity().currency().post(e);

        return e;
    }

    private void getAsserts(CurrencyEntity e, CurrencyEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getIsoCode(), retrievedEntity.getIsoCode());
        assertEquals(e.getMajorUnit(), retrievedEntity.getMajorUnit());
        assertEquals(e.getMinorUnit(), retrievedEntity.getMinorUnit());
    }

    private void putAsserts(CurrencyEntity e, CurrencyEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CurrencyEntity retrievedUpdatedEntity = api.entity().currency().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getArchived(), retrievedUpdatedEntity.getArchived());
        assertEquals(retrievedOriginalEntity.getCode(), retrievedUpdatedEntity.getCode());
        assertEquals(retrievedOriginalEntity.getIsoCode(), retrievedUpdatedEntity.getIsoCode());
        assertEquals(retrievedOriginalEntity.getMajorUnit(), retrievedUpdatedEntity.getMajorUnit());
        assertEquals(retrievedOriginalEntity.getMinorUnit(), retrievedUpdatedEntity.getMinorUnit());
    }
}

