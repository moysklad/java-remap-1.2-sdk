package ru.moysklad.remap_1_2.entities;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.OrderParam;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AssortmentTest extends EntityTestBase {

    @Ignore // TODO снять игнор
    @Test
    public void getTest() throws IOException, ApiClientException {
        ListEntity<Assortment> assortment = api.entity().assortment().get();

        assertNotNull(assortment.getRows());
        Integer countBefore = assortment.getMeta().getSize();

        Product product = simpleEntityManager.createSimpleProduct();

        assortment = api.entity().assortment().get(OrderParam.order("updated", OrderParam.Direction.desc));
        assertEquals(Integer.valueOf(countBefore + 1), assortment.getMeta().getSize());
        assertEquals(product, assortment.getRows().get(0));
    }

    @Test
    public void settingsTest() throws IOException, ApiClientException {
        AssortmentSettings assortmentSettings = api.entity().assortment().settings().get();
        final BarcodeRules barcodeRules= assortmentSettings.getBarcodeRules();
        final UniqueCodeRules uniqueCodeRules = assortmentSettings.getUniqueCodeRules();
        final Boolean createdShared = assortmentSettings.getCreatedShared();
        assortmentSettings.setBarcodeRules(new BarcodeRules(!barcodeRules.getFillEAN13Barcode(), !barcodeRules.getWeightBarcode(), barcodeRules.getWeightBarcodePrefix()));
        assortmentSettings.setUniqueCodeRules(new UniqueCodeRules(!uniqueCodeRules.getCheckUniqueCode(), !uniqueCodeRules.getFillUniqueCode()));
        assortmentSettings.setCreatedShared(!createdShared);
        api.entity().assortment().settings().update(assortmentSettings);
        assortmentSettings = api.entity().assortment().settings().get();
        assertEquals(!barcodeRules.getFillEAN13Barcode(), assortmentSettings.getBarcodeRules().getFillEAN13Barcode());
        assertEquals(!barcodeRules.getWeightBarcode(), assortmentSettings.getBarcodeRules().getWeightBarcode());
        assertEquals(!uniqueCodeRules.getCheckUniqueCode(), assortmentSettings.getUniqueCodeRules().getCheckUniqueCode());
        assertEquals(!uniqueCodeRules.getFillUniqueCode(), assortmentSettings.getUniqueCodeRules().getFillUniqueCode());
        assertEquals(!createdShared, assortmentSettings.getCreatedShared());
        //revert changes
        assortmentSettings.setBarcodeRules(new BarcodeRules(barcodeRules.getFillEAN13Barcode(), barcodeRules.getWeightBarcode(), barcodeRules.getWeightBarcodePrefix()));
        assortmentSettings.setUniqueCodeRules(new UniqueCodeRules(uniqueCodeRules.getCheckUniqueCode(), uniqueCodeRules.getFillUniqueCode()));
        assortmentSettings.setCreatedShared(createdShared);
        api.entity().assortment().settings().update(assortmentSettings);
    }
}
