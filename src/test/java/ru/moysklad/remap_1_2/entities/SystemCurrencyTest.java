package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;

public class SystemCurrencyTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        SystemCurrency currency = new SystemCurrency();
        currency.setArchived(false);
        currency.setIsoCode("RUB");

        int sizeRubCurrencies = api.entity().currency().get(filterEq("name", "руб")).getRows().size();

        api.entity().systemcurrency().create(currency);

        ListEntity<SystemCurrency> updatedEntitiesList = api.entity().systemcurrency().get(filterEq("name", currency.getName()));

        assertEquals(sizeRubCurrencies + 1, updatedEntitiesList.getRows().size());

        SystemCurrency retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(currency.getName(), retrievedEntity.getName());
        assertEquals(currency.getArchived(), retrievedEntity.getArchived());
        assertEquals(currency.getCode(), retrievedEntity.getCode());
        assertEquals(currency.getIsoCode(), retrievedEntity.getIsoCode());
        assertEquals(currency.getMajorUnit(), retrievedEntity.getMajorUnit());
        assertEquals(currency.getMinorUnit(), retrievedEntity.getMinorUnit());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        SystemCurrency originalCurrency = (SystemCurrency) originalEntity;
        SystemCurrency retrievedCurrency = (SystemCurrency) retrievedEntity;

        assertEquals(originalCurrency.getName(), retrievedCurrency.getName());
        assertEquals(originalCurrency.getCode(), retrievedCurrency.getCode());
        assertEquals(originalCurrency.getIsoCode(), retrievedCurrency.getIsoCode());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        SystemCurrency originalCurrency = (SystemCurrency) originalEntity;
        SystemCurrency updatedCurrency = (SystemCurrency) updatedEntity;

        assertNotEquals(originalCurrency.getName(), updatedCurrency.getName());
        assertEquals(changedField, updatedCurrency.getName());
        assertEquals(originalCurrency.getCode(), updatedCurrency.getCode());
        assertEquals(originalCurrency.getIsoCode(), updatedCurrency.getIsoCode());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().systemcurrency();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return SystemCurrency.class;
    }
}

