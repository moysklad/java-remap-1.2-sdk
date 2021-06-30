package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CurrencyTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
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
    public EntityClientBase entityClient() {
        return api.entity().currency();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Currency.class;
    }
}

