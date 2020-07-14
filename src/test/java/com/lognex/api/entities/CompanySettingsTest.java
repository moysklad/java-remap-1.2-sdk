package com.lognex.api.entities;

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
        CompanySettings response = api.entity().companysettings().get();
        ListEntity<Currency> currency = api.entity().currency().get(filterEq("isoCode", "RUB"));
        assertEquals(1, currency.getRows().size());

        assertEquals(response.getCurrency(), currency.getRows().get(0));
        assertTrue(response.getPriceTypes().size() > 0);
        assertTrue(response.getPriceTypes().stream().anyMatch(p -> p.getName().equals("Цена продажи")));
        assertEquals(CompanySettings.DiscountStrategy.bySum, response.getDiscountStrategy());
        assertEquals(false, response.getGlobalOperationNumbering());
        assertEquals(false, response.getCheckShippingStock());
        assertEquals(false, response.getCheckMinPrice());
        assertEquals(true, response.getUseRecycleBin());
        assertEquals(false, response.getUseCompanyAddress());
    }

    @Test
    public void updateTest() throws IOException, ApiClientException {
        CompanySettings companySettings = api.entity().companysettings().get();
        companySettings.setGlobalOperationNumbering(true);
        companySettings.setCheckShippingStock(true);
        companySettings.setCheckMinPrice(true);
        companySettings.setUseRecycleBin(false);
        companySettings.setUseCompanyAddress(true);
        companySettings.setCompanyAddress("123@123.ru");
        api.entity().companysettings().update(companySettings);
        companySettings = api.entity().companysettings().get();
        assertEquals(true, companySettings.getGlobalOperationNumbering());
        assertEquals(true, companySettings.getCheckShippingStock());
        assertEquals(true, companySettings.getCheckMinPrice());
        assertEquals(false, companySettings.getUseRecycleBin());
        assertEquals(true, companySettings.getUseCompanyAddress());
        assertEquals("123@123.ru", companySettings.getCompanyAddress());
        //revert changes
        companySettings.setGlobalOperationNumbering(false);
        companySettings.setCheckShippingStock(false);
        companySettings.setCheckMinPrice(false);
        companySettings.setUseRecycleBin(true);
        companySettings.setUseCompanyAddress(false);
        companySettings.setCompanyAddress(null);
        api.entity().companysettings().update(companySettings);
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
