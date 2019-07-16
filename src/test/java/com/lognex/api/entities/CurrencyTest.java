package com.lognex.api.entities;

import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CurrencyTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        Currency currency = new Currency();
        currency.setName("currency_" + randomString(3) + "_" + new Date().getTime());
        currency.setArchived(false);
        currency.setCode(randomString(3));
        currency.setIsoCode(randomString(3));

        Currency.Unit major = new Currency.Unit();
        major.setGender(Currency.Unit.Gender.masculine);
        major.setS1(randomString());
        major.setS2(randomString());
        major.setS5(randomString());
        currency.setMajorUnit(major);

        Currency.Unit minor = new Currency.Unit();
        minor.setGender(Currency.Unit.Gender.feminine);
        minor.setS1(randomString());
        minor.setS2(randomString());
        minor.setS5(randomString());
        currency.setMinorUnit(minor);

        api.entity().currency().create(currency);

        ListEntity<Currency> updatedEntitiesList = api.entity().currency().get(filterEq("name", currency.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Currency retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(currency.getName(), retrievedEntity.getName());
        assertEquals(currency.getArchived(), retrievedEntity.getArchived());
        assertEquals(currency.getCode(), retrievedEntity.getCode());
        assertEquals(currency.getIsoCode(), retrievedEntity.getIsoCode());
        assertEquals(currency.getMajorUnit(), retrievedEntity.getMajorUnit());
        assertEquals(currency.getMinorUnit(), retrievedEntity.getMinorUnit());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Currency originalCurrency = (Currency) originalEntity;
        Currency retrievedCurrency = (Currency) retrievedEntity;

        assertEquals(originalCurrency.getName(), retrievedCurrency.getName());
        assertEquals(originalCurrency.getCode(), retrievedCurrency.getCode());
        assertEquals(originalCurrency.getIsoCode(), retrievedCurrency.getIsoCode());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Currency originalCurrency = (Currency) originalEntity;
        Currency updatedCurrency = (Currency) updatedEntity;

        assertNotEquals(originalCurrency.getName(), updatedCurrency.getName());
        assertEquals(changedField, updatedCurrency.getName());
        assertEquals(originalCurrency.getCode(), updatedCurrency.getCode());
        assertEquals(originalCurrency.getIsoCode(), updatedCurrency.getIsoCode());
    }

    @Override
    protected EntityApiClient entityClient() {
        return api.entity().currency();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Currency.class;
    }
}

