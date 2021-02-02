package ru.moysklad.remap_1_2.entities;

import ru.moysklad.remap_1_2.utils.ApiClientException;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class PriceTypeTest extends EntityTestBase {
    @Test
    public void getPriceTypes() throws IOException, ApiClientException {
        PriceType sellPriceType = getSellPriceType();

        PriceType priceType = api.entity().companysettings().pricetype().get(sellPriceType.getId());
        assertEquals(priceType, sellPriceType);

        priceType = api.entity().companysettings().pricetype().get(sellPriceType);
        assertEquals(priceType, sellPriceType);
    }

    private PriceType getSellPriceType() throws IOException, ApiClientException {
        List<PriceType> priceTypeListEntity = api.entity().companysettings().pricetype().get();
        
        assertTrue(priceTypeListEntity.size() > 0);
        Optional<PriceType> sellPriceTypeOpt = priceTypeListEntity.stream()
                .filter(pt -> pt.getName().equals("Цена продажи"))
                .findFirst();
        assertTrue(sellPriceTypeOpt.isPresent());

        return sellPriceTypeOpt.get();
    }

    @Test
    public void getDefaultPriceTypeTest() throws IOException, ApiClientException {
        PriceType sellPriceType = getSellPriceType();
        PriceType defaultPriceType = api.entity().companysettings().pricetype().getDefault();

        assertEquals(sellPriceType, defaultPriceType);
    }

    @Test
    public void updatePriceTypesTest() throws IOException, ApiClientException {
        List<PriceType> priceTypeListEntity = api.entity().companysettings().pricetype().get();
        int initSize = priceTypeListEntity.size();

        PriceType newPriceType = new PriceType();
        newPriceType.setName("pricetype_" + randomStringTail());
        newPriceType.setExternalCode(randomString());
        priceTypeListEntity.add(newPriceType);

        api.entity().companysettings().pricetype().updatePriceTypes(priceTypeListEntity);

        priceTypeListEntity = api.entity().companysettings().pricetype().get();
        assertNotNull(priceTypeListEntity);
        assertEquals(initSize + 1, priceTypeListEntity.size());
        Optional<PriceType> retrievedNewPriceType = priceTypeListEntity.stream()
                .filter(pt -> pt.getName().equals(newPriceType.getName()) &&
                        pt.getExternalCode().equals(newPriceType.getExternalCode()))
                .findFirst();
        assertTrue(retrievedNewPriceType.isPresent());

        priceTypeListEntity.remove(retrievedNewPriceType.get());
        api.entity().companysettings().pricetype().updatePriceTypes(priceTypeListEntity);

        priceTypeListEntity = api.entity().companysettings().pricetype().get();
        assertNotNull(priceTypeListEntity);
        assertEquals(initSize, priceTypeListEntity.size());
        retrievedNewPriceType = priceTypeListEntity.stream()
                .filter(pt -> pt.getName().equals(newPriceType.getName()) &&
                        pt.getExternalCode().equals(newPriceType.getExternalCode()))
                .findFirst();
        assertFalse(retrievedNewPriceType.isPresent());
    }
}
