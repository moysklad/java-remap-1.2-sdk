package com.lognex.api.entities;

import com.lognex.api.responses.CompanySettingsResponse;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CompanySettingsMetadata;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class CompanySettingsTest extends EntityTestBase {
    @Test
    public void getTest() throws IOException, ApiClientException {
        CompanySettingsResponse response = api.entity().companysettings().get();
        ListEntity<Currency> currency = api.entity().currency().get(filterEq("isoCode", "RUB"));
        assertEquals(1, currency.getRows().size());

        assertEquals(response.getCurrency(), currency.getRows().get(0));
        assertTrue(response.getPriceTypes().size() > 0);
        assertTrue(response.getPriceTypes().stream().anyMatch(p -> p.getName().equals("Цена продажи")));
        assertEquals(CompanySettingsResponse.DiscountStrategy.bySum, response.getDiscountStrategy());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);
        CompanySettingsMetadata metadata = api.entity().companysettings().metadata();

        assertTrue(metadata.getCustomEntities().stream().
                anyMatch(x -> x.getEntityMeta().getName().equals(customEntity.getName()))
        );
    }
}
