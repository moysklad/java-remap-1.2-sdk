package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
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

public class VariantEntityTest extends EntityGetDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        VariantEntity variant = new VariantEntity();
        variant.setArchived(false);
        variant.setProduct(simpleEntityManager.createSimpleProduct());

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
    public void putTest() throws IOException, LognexApiException {
        VariantEntity variant = simpleEntityManager.createSimpleVariant();

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
    public void metadataTest() throws IOException, LognexApiException {
        VariantEntity variant = simpleEntityManager.createSimpleVariant();

        VariantMetadataResponse metadata = api.entity().variant().metadata();

        assertTrue(metadata.getCharacteristics().stream().
                    anyMatch(x -> x.getName().equals(variant.getCharacteristics().get(0).getName()))
        );
    }

    private void putAsserts(VariantEntity variant, VariantEntity retrievedOriginalEntity, String value) throws IOException, LognexApiException {
        VariantEntity retrievedUpdatedEntity = api.entity().variant().get(variant.getId(), expand("product"));

        assertNotEquals(retrievedOriginalEntity.getCharacteristics().get(0).getValue(),
                        retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(value, retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(retrievedOriginalEntity.getCharacteristics().size(), retrievedUpdatedEntity.getCharacteristics().size());
        assertEquals(retrievedOriginalEntity.getProduct().getMeta().getHref(), retrievedUpdatedEntity.getProduct().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        VariantEntity originalVariant = (VariantEntity) originalEntity;
        VariantEntity retrievedVariant = (VariantEntity) retrievedEntity;

        assertEquals(originalVariant.getName(), retrievedVariant.getName());
        assertEquals(originalVariant.getProduct().getName(), retrievedVariant.getProduct().getName());
        assertEquals(originalVariant.getCharacteristics().size(), retrievedVariant.getCharacteristics().size());
        assertEquals(originalVariant.getCharacteristics().get(0), retrievedVariant.getCharacteristics().get(0));
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().variant();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return VariantEntity.class;
    }
}
