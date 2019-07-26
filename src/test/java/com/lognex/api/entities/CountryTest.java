package com.lognex.api.entities;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CountryTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Country country = new Country();
        country.setName("country_" + randomString(3) + "_" + new Date().getTime());
        country.setCode(randomString(3));
        country.setExternalCode(randomString(5));
        
        api.entity().country().create(country);

        ListEntity<Country> updatedEntitiesList = api.entity().country().get(filterEq("name", country.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Country retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(country.getName(), retrievedEntity.getName());
        assertEquals(country.getCode(), retrievedEntity.getCode());
        assertEquals(country.getExternalCode(), retrievedEntity.getExternalCode());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Country originalCountry = (Country) originalEntity;
        Country retrievedCountry = (Country) retrievedEntity;

        assertEquals(originalCountry.getName(), retrievedCountry.getName());
        assertEquals(originalCountry.getCode(), retrievedCountry.getCode());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Country originalCountry = (Country) originalEntity;
        Country updatedCountry = (Country) updatedEntity;

        assertNotEquals(originalCountry.getName(), updatedCountry.getName());
        assertEquals(changedField, updatedCountry.getName());
        assertEquals(originalCountry.getCode(), updatedCountry.getCode());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().country();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Country.class;
    }
}
