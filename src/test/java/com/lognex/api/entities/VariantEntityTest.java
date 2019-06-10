package com.lognex.api.entities;

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
        VariantEntity variant = new VariantEntity();
        variant.setArchived(false);
        variant.setProduct(simpleEntityFactory.createSimpleProduct());

        VariantEntity.Characteristic characteristic = new VariantEntity.Characteristic();
        characteristic.setName(randomString());
        characteristic.setValue(randomString());
        variant.setCharacteristics(new ArrayList<>());
        variant.getCharacteristics().add(characteristic);

        api.entity().variant().post(variant);

        ListEntity<VariantEntity> updatedEntitiesList = api.entity().variant().get(filterEq("name", variant.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        VariantEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(variant.getName(), retrievedEntity.getName());
        assertEquals(variant.getArchived(), retrievedEntity.getArchived());
        assertEquals(variant.getProduct().getMeta().getHref(), retrievedEntity.getProduct().getMeta().getHref());
        assertEquals(variant.getCharacteristics().size(), retrievedEntity.getCharacteristics().size());
        assertEquals(variant.getCharacteristics().get(0).getName(), retrievedEntity.getCharacteristics().get(0).getName());
        assertEquals(variant.getCharacteristics().get(0).getValue(), retrievedEntity.getCharacteristics().get(0).getValue());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        VariantEntity variant = simpleEntityFactory.createSimpleVariant();

        VariantEntity retrievedEntity = api.entity().variant().get(variant.getId());
        getAsserts(variant, retrievedEntity);

        retrievedEntity = api.entity().variant().get(variant);
        getAsserts(variant, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        VariantEntity variant = simpleEntityFactory.createSimpleVariant();

        VariantEntity retrievedOriginalEntity = api.entity().variant().get(variant.getId());
        String value = "mod_" + randomString(3) + "_" + new Date().getTime();
        variant.getCharacteristics().get(0).setValue(value);
        api.entity().variant().put(variant.getId(), variant);
        putAsserts(variant, retrievedOriginalEntity, value);

        retrievedOriginalEntity.set(variant);
        variant.setCharacteristics(new ArrayList<>());
        VariantEntity.Characteristic characteristic = new VariantEntity.Characteristic();
        characteristic.setName(randomString());
        value = "mod_" + randomString(3) + "_" + new Date().getTime();
        characteristic.setValue(value);
        variant.getCharacteristics().add(characteristic);

        api.entity().variant().put(variant);
        putAsserts(variant, retrievedOriginalEntity, value);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        VariantEntity variant = simpleEntityFactory.createSimpleVariant();

        ListEntity<VariantEntity> entitiesList = api.entity().variant().get(filterEq("name", variant.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().variant().delete(variant.getId());

        entitiesList = api.entity().variant().get(filterEq("name", variant.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        VariantEntity variant = simpleEntityFactory.createSimpleVariant();

        ListEntity<VariantEntity> entitiesList = api.entity().variant().get(filterEq("name", variant.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().variant().delete(variant);

        entitiesList = api.entity().variant().get(filterEq("name", variant.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        VariantEntity variant = simpleEntityFactory.createSimpleVariant();

        VariantMetadataResponse metadata = api.entity().variant().metadata();

        assertTrue(metadata.getCharacteristics().stream().
                    anyMatch(x -> x.getName().equals(variant.getCharacteristics().get(0).getName()))
        );
    }

    private void getAsserts(VariantEntity variant, VariantEntity retrievedEntity) {
        assertEquals(variant.getName(), retrievedEntity.getName());
        assertEquals(variant.getProduct().getName(), retrievedEntity.getProduct().getName());
        assertEquals(variant.getCharacteristics().size(), retrievedEntity.getCharacteristics().size());
        assertEquals(variant.getCharacteristics().get(0), retrievedEntity.getCharacteristics().get(0));
    }

    private void putAsserts(VariantEntity variant, VariantEntity retrievedOriginalEntity, String value) throws IOException, LognexApiException {
        VariantEntity retrievedUpdatedEntity = api.entity().variant().get(variant.getId(), expand("product"));

        assertNotEquals(retrievedOriginalEntity.getCharacteristics().get(0).getValue(),
                        retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(value, retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(retrievedOriginalEntity.getCharacteristics().size(), retrievedUpdatedEntity.getCharacteristics().size());
        assertEquals(retrievedOriginalEntity.getProduct().getMeta().getHref(), retrievedUpdatedEntity.getProduct().getMeta().getHref());
    }
}
