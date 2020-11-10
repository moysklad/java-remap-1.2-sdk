package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CompanySettingsMetadata;
import com.lognex.api.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;

import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class AssortmentSettingsTest extends EntityTestBase {
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
        final Boolean globalOperationNumbering = companySettings.getGlobalOperationNumbering();
        final Boolean checkShippingStock = companySettings.getCheckShippingStock();
        final Boolean checkMinPrice = companySettings.getCheckMinPrice();
        final Boolean useCompanyAddress = companySettings.getUseCompanyAddress();
        final Boolean useRecycleBin = companySettings.getUseRecycleBin();
        final String companyAddress = companySettings.getCompanyAddress();
        final String testAddress = "123@123.ru";
        companySettings.setGlobalOperationNumbering(!globalOperationNumbering);
        companySettings.setCheckShippingStock(!checkShippingStock);
        companySettings.setCheckMinPrice(!checkMinPrice);
        companySettings.setUseRecycleBin(!useRecycleBin);
        companySettings.setUseCompanyAddress(!useCompanyAddress);
        companySettings.setCompanyAddress(testAddress);
        api.entity().companysettings().update(companySettings);
        companySettings = api.entity().companysettings().get();
        assertEquals(!globalOperationNumbering, companySettings.getGlobalOperationNumbering());
        assertEquals(!checkShippingStock, companySettings.getCheckShippingStock());
        assertEquals(!checkMinPrice, companySettings.getCheckMinPrice());
        assertEquals(!useRecycleBin, companySettings.getUseRecycleBin());
        assertEquals(!useCompanyAddress, companySettings.getUseCompanyAddress());
        assertEquals(testAddress, companySettings.getCompanyAddress());
        //revert changes
        companySettings.setGlobalOperationNumbering(globalOperationNumbering);
        companySettings.setCheckShippingStock(checkShippingStock);
        companySettings.setCheckMinPrice(checkMinPrice);
        companySettings.setUseRecycleBin(useRecycleBin);
        companySettings.setUseCompanyAddress(useCompanyAddress);
        companySettings.setCompanyAddress(companyAddress);
        api.entity().companysettings().update(companySettings);
    }
}
