package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CurrencyEntityTest extends EntityGetUpdateDeleteTest {
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

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        CurrencyEntity originalCurrency = (CurrencyEntity) originalEntity;
        CurrencyEntity retrievedCurrency = (CurrencyEntity) retrievedEntity;

        assertEquals(originalCurrency.getName(), retrievedCurrency.getName());
        assertEquals(originalCurrency.getCode(), retrievedCurrency.getCode());
        assertEquals(originalCurrency.getIsoCode(), retrievedCurrency.getIsoCode());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        CurrencyEntity originalCurrency = (CurrencyEntity) originalEntity;
        CurrencyEntity updatedCurrency = (CurrencyEntity) updatedEntity;

        assertNotEquals(originalCurrency.getName(), updatedCurrency.getName());
        assertEquals(changedField, updatedCurrency.getName());
        assertEquals(originalCurrency.getCode(), updatedCurrency.getCode());
        assertEquals(originalCurrency.getIsoCode(), updatedCurrency.getIsoCode());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().currency();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return CurrencyEntity.class;
    }
}

