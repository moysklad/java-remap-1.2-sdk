package ru.moysklad.remap_1_2.entities;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.products.Variant;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.VariantMetadataResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class VariantTest extends EntityGetUpdateDeleteWithImageTest<Variant> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Variant variant = new Variant();
        variant.setArchived(false);
        variant.setProduct(simpleEntityManager.createSimpleProduct());

        Variant.Characteristic characteristic = new Variant.Characteristic();
        characteristic.setName(randomString());
        characteristic.setValue(randomString());
        variant.setCharacteristics(new ArrayList<>());
        variant.getCharacteristics().add(characteristic);

        api.entity().variant().create(variant);

        ListEntity<Variant> updatedEntitiesList = api.entity().variant().get(filterEq("name", variant.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Variant retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(variant.getName(), retrievedEntity.getName());
        assertEquals(variant.getArchived(), retrievedEntity.getArchived());
        assertEquals(variant.getProduct().getMeta().getHref(), retrievedEntity.getProduct().getMeta().getHref());
        assertEquals(variant.getCharacteristics().size(), retrievedEntity.getCharacteristics().size());
        assertEquals(variant.getCharacteristics().get(0).getName(), retrievedEntity.getCharacteristics().get(0).getName());
        assertEquals(variant.getCharacteristics().get(0).getValue(), retrievedEntity.getCharacteristics().get(0).getValue());
    }

    @Override
    @Test
    public void putTest() throws IOException, ApiClientException {
        Variant variant = simpleEntityManager.createSimple(Variant.class);

        Variant retrievedOriginalEntity = api.entity().variant().get(variant.getId());
        String value = "mod_" + randomString(3) + "_" + new Date().getTime();
        variant.getCharacteristics().get(0).setValue(value);
        api.entity().variant().update(variant.getId(), variant);
        putAsserts(variant, retrievedOriginalEntity, value);

        retrievedOriginalEntity.set(variant);
        variant.setCharacteristics(new ArrayList<>());
        Variant.Characteristic characteristic = new Variant.Characteristic();
        characteristic.setName(randomString());
        value = "mod_" + randomString(3) + "_" + new Date().getTime();
        characteristic.setValue(value);
        variant.getCharacteristics().add(characteristic);

        api.entity().variant().update(variant);
        putAsserts(variant, retrievedOriginalEntity, value);
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {

    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        Variant variant = simpleEntityManager.createSimple(Variant.class);

        VariantMetadataResponse metadata = api.entity().variant().metadata();

        assertTrue(metadata.getCharacteristics().stream().
                    anyMatch(x -> x.getName().equals(variant.getCharacteristics().get(0).getName()))
        );
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().variant().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setDescription("description");
        Attribute created = api.entity().variant().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest2() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        Attribute created = api.entity().variant().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        Attribute updated = api.entity().variant().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        Attribute created = api.entity().variant().createMetadataAttribute(attribute);

        api.entity().variant().deleteMetadataAttribute(created);

        try {
            api.entity().variant().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    private void putAsserts(Variant variant, Variant retrievedOriginalEntity, String value) throws IOException, ApiClientException {
        Variant retrievedUpdatedEntity = api.entity().variant().get(variant.getId(), expand("product"));

        assertNotEquals(retrievedOriginalEntity.getCharacteristics().get(0).getValue(),
                        retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(value, retrievedUpdatedEntity.getCharacteristics().get(0).getValue());
        assertEquals(retrievedOriginalEntity.getCharacteristics().size(), retrievedUpdatedEntity.getCharacteristics().size());
        assertEquals(retrievedOriginalEntity.getProduct().getMeta().getHref(), retrievedUpdatedEntity.getProduct().getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Variant originalVariant = (Variant) originalEntity;
        Variant retrievedVariant = (Variant) retrievedEntity;

        assertEquals(originalVariant.getName(), retrievedVariant.getName());
        assertEquals(originalVariant.getProduct().getName(), retrievedVariant.getProduct().getName());
        assertEquals(originalVariant.getCharacteristics().size(), retrievedVariant.getCharacteristics().size());
        assertEquals(originalVariant.getCharacteristics().get(0), retrievedVariant.getCharacteristics().get(0));
    }

    @Ignore
    @Override
    public void massCreateDeleteTest()  {
        // удаление модификации может привести к удалению характеристики и падению при создании
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().variant();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Variant.class;
    }
}
