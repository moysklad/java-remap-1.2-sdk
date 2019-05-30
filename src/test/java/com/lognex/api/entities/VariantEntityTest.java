package com.lognex.api.entities;

import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.entities.products.VariantEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.VariantMetadataResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class VariantEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        VariantEntity e = new VariantEntity();
        e.setArchived(false);

        ProductEntity product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        api.entity().product().post(product);
        e.setProduct(product);

        VariantEntity.Characteristic characteristic = new VariantEntity.Characteristic();
        characteristic.setName(randomString());
        characteristic.setValue(randomString());
        e.setCharacteristics(new ArrayList<>());
        e.getCharacteristics().add(characteristic);

        api.entity().variant().post(e);

        ListEntity<VariantEntity> updatedEntitiesList = api.entity().variant().get(filterEq("name", e.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        VariantEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        VariantEntity e = createSimpleVariant();

        VariantEntity retrievedEntity = api.entity().variant().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().variant().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        VariantEntity e = createSimpleVariant();

        VariantEntity retrievedOriginalEntity = api.entity().variant().get(e.getId());
        String value = "mod_" + randomString(3) + "_" + new Date().getTime();
        e.getCharacteristics().get(0).setValue(value);
        api.entity().variant().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, value);

        retrievedOriginalEntity.set(e);
        e.setCharacteristics(new ArrayList<>());
        VariantEntity.Characteristic characteristic = new VariantEntity.Characteristic();
        characteristic.setName(randomString());
        value = "mod_" + randomString(3) + "_" + new Date().getTime();
        characteristic.setValue(value);
        e.getCharacteristics().add(characteristic);

        api.entity().variant().put(e);
        putAsserts(e, retrievedOriginalEntity, value);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        VariantEntity e = createSimpleVariant();

        ListEntity<VariantEntity> entitiesList = api.entity().variant().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().variant().delete(e.getId());

        entitiesList = api.entity().variant().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        VariantEntity e = createSimpleVariant();

        ListEntity<VariantEntity> entitiesList = api.entity().variant().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().variant().delete(e);

        entitiesList = api.entity().variant().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        VariantEntity e = createSimpleVariant();

        VariantMetadataResponse metadata = api.entity().variant().metadata();

        assertTrue(metadata.getCharacteristics().stream().
                    anyMatch(x -> x.getName().equals(e.getCharacteristics().get(0).getName()))
        );
    }

    private VariantEntity createSimpleVariant() throws IOException, LognexApiException {
        VariantEntity e = new VariantEntity();
        e.setArchived(false);

        ProductEntity product = new ProductEntity();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        api.entity().product().post(product);
        e.setProduct(product);

        VariantEntity.Characteristic characteristic = new VariantEntity.Characteristic();
        characteristic.setName(randomString());
        characteristic.setValue(randomString());
        e.setCharacteristics(new ArrayList<>());
        e.getCharacteristics().add(characteristic);

        api.entity().variant().post(e);

        return e;
    }

    private void getAsserts(VariantEntity e, VariantEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getArchived(), retrievedEntity.getArchived());
        assertEquals(e.getProduct().getName(), retrievedEntity.getProduct().getName());
        assertEquals(e.getCharacteristics().size(), retrievedEntity.getCharacteristics().size());
        assertEquals(e.getCharacteristics().get(0), retrievedEntity.getCharacteristics().get(0));
    }

    private void putAsserts(VariantEntity e, VariantEntity retrievedOriginalEntity, String value) throws IOException, LognexApiException {
        VariantEntity retrievedUpdatedEntity = api.entity().variant().get(e.getId(), expand("product"));

        assertNotEquals(retrievedOriginalEntity.getCharacteristics().get(0).getValue(),
                        retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(value, retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(retrievedOriginalEntity.getArchived(), retrievedUpdatedEntity.getArchived());
        assertEquals(retrievedOriginalEntity.getCharacteristics().size(), retrievedUpdatedEntity.getCharacteristics().size());
        assertEquals(retrievedOriginalEntity.getProduct().getMeta().getHref(), retrievedUpdatedEntity.getProduct().getMeta().getHref());
    }
}
