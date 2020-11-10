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
    public void updateTest() throws IOException, ApiClientException {
        AssortmentSettings assortmentSettings = api.entity().assortmentsettings().get();
        final BarcodeRules barcodeRules= assortmentSettings.getBarcodeRules();
        final UniqueCodeRules uniqueCodeRules = assortmentSettings.getUniqueCodeRules();
        final Boolean createdShared = assortmentSettings.getCratedShared();
        final String testAddress = "123@123.ru";
        assortmentSettings.setBarcodeRules(new BarcodeRules(!barcodeRules.getFillEAN13Barcode(), !barcodeRules.getWeightBarcode(), barcodeRules.getWeightBarcodePrefix());
        assortmentSettings.setUniqueCodeRules(new UniqueCodeRules(!uniqueCodeRules.getCheckUniqueCode(), !uniqueCodeRules.getFillUniqueCode()));
        assortmentSettings.setCratedShared(!createdShared);
        api.entity().assortmentsettings().update(assortmentSettings);
        assortmentSettings = api.entity().assortmentsettings().get();
        assertEquals(!barcodeRules.getFillEAN13Barcode(), assortmentSettings.getBarcodeRules().getFillEAN13Barcode());
        assertEquals(!barcodeRules.getWeightBarcode(), assortmentSettings.getBarcodeRules().getWeightBarcode());
        assertEquals(!uniqueCodeRules.getCheckUniqueCode(), assortmentSettings.getUniqueCodeRules().getCheckUniqueCode());
        assertEquals(!uniqueCodeRules.getFillUniqueCode(), assortmentSettings.getUniqueCodeRules().getFillUniqueCode());
        assertEquals(!createdShared, assortmentSettings.getCratedShared());
        //revert changes
        assortmentSettings.setBarcodeRules(new BarcodeRules(barcodeRules.getFillEAN13Barcode(), barcodeRules.getWeightBarcode(), barcodeRules.getWeightBarcodePrefix());
        assortmentSettings.setUniqueCodeRules(new UniqueCodeRules(uniqueCodeRules.getCheckUniqueCode(), uniqueCodeRules.getFillUniqueCode()));
        assortmentSettings.setCratedShared(!createdShared);
        api.entity().assortmentsettings().update(assortmentSettings);
    }
}
