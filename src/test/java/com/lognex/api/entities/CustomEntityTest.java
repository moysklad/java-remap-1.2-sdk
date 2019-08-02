package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CompanySettingsMetadata.CustomEntityMetadata;
import com.lognex.api.utils.ApiClientException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class CustomEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, ApiClientException {
        CustomEntity customEntity = new CustomEntity();
        customEntity.setName("custom_entity_" + randomString(3) + "_" + new Date().getTime());

        api.entity().customentity().create(customEntity);

        CustomEntity retrievedEntity = getCustomEntityByHref(customEntity.getMeta().getHref());

        assertNotNull(retrievedEntity);
        getAsserts(customEntity, retrievedEntity);
    }

    @Test
    public void putByIdTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(customEntity.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        String name = "custom_entity_" + randomString(3) + "_" + new Date().getTime();
        customEntity.setName(name);

        api.entity().customentity().update(customEntity.getId(), customEntity);
        putAsserts(customEntity, retrievedOriginalEntity, name);
    }

    @Test
    public void putEntityTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(customEntity.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        String name = "custom_entity_" + randomString(3) + "_" + new Date().getTime();
        customEntity.setName(name);

        api.entity().customentity().update(customEntity);
        putAsserts(customEntity, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteByIdTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class, true);

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(customEntity.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().delete(customEntity.getId());
        simpleEntityManager.removeSimpleFromPool(customEntity);

        CustomEntity retrievedNullEntity = getCustomEntityByHref(customEntity.getMeta().getHref());
        assertNull(retrievedNullEntity);
    }

    @Test
    public void deleteEntityTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class, true);

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(customEntity.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().delete(customEntity);
        simpleEntityManager.removeSimpleFromPool(customEntity);

        CustomEntity retrievedNullEntity = getCustomEntityByHref(customEntity.getMeta().getHref());
        assertNull(retrievedNullEntity);
    }

    @Test
    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public void errorTest() throws IOException {
        try {
            CustomEntity customEntity = new CustomEntity();
            api.entity().customentity().create(customEntity);
            fail("Ожидалось исключение");
        } catch (ApiClientException customEntity) {
            assertApiError(
                    customEntity, 412,
                    Arrays.asList(
                            Pair.of(3000, "Ошибка сохранения объекта: поле 'name' не может быть пустым или отсутствовать")
                    )
            );
        }
    }

    @Test
    public void postElementTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);
        CustomEntityElement customEntityElement = new CustomEntityElement();
        customEntityElement.setName("custom_entity_element_" + randomString(3) + "_" + new Date().getTime());

        api.entity().customentity().createCustomEntityElement(customEntity.getId(), customEntityElement);

        CustomEntityElement retrievedEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), customEntityElement.getId());

        assertNotNull(retrievedEntity);
        assertEquals(customEntityElement.getId(), retrievedEntity.getId());
        assertEquals(customEntityElement.getName(), retrievedEntity.getName());
    }

    @Test
    public void getElementTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);
        CustomEntityElement customEntityElement = simpleEntityManager.createSimpleCustomEntityElement(customEntity);

        CustomEntityElement retrievedEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), customEntityElement.getId());

        getAsserts(customEntityElement, retrievedEntity);
    }

    @Test
    public void getByIdElementsTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class, true);

        List<CustomEntityElement> customEntityElementList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customEntityElementList.add(simpleEntityManager.createSimpleCustomEntityElement(customEntity));
        }

        List<CustomEntityElement> retrievedEntities = api.entity().customentity().getCustomEntityElements(customEntity.getId()).getRows();

        assertEquals(3, retrievedEntities.size());

        for (int i = 0; i < 3; i++) {
            getAsserts(customEntityElementList.get(i), retrievedEntities.get(i));
        }
    }

    @Test
    public void getEntityElementsTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class, true);

        List<CustomEntityElement> customEntityElementList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customEntityElementList.add(simpleEntityManager.createSimpleCustomEntityElement(customEntity));
        }

        List<CustomEntityElement> retrievedEntities = api.entity().customentity().getCustomEntityElements(customEntity).getRows();

        assertEquals(3, retrievedEntities.size());

        for (int i = 0; i < 3; i++) {
            getAsserts(customEntityElementList.get(i), retrievedEntities.get(i));
        }
    }

    @Test
    public void putByIdElementTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);
        CustomEntityElement customEntityElement = simpleEntityManager.createSimpleCustomEntityElement(customEntity);

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), customEntityElement.getId());
        assertNotNull(retrievedOriginalEntity);

        String code = randomString(3);
        customEntityElement.setCode(code);
        customEntityElement.setDescription("");

        api.entity().customentity().updateCustomEntityElement(customEntity.getId(), customEntityElement.getId(), customEntityElement);
        putAsserts(customEntity, customEntityElement, retrievedOriginalEntity, code);
    }

    @Test
    public void putEntityElementTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);
        CustomEntityElement customEntityElement = simpleEntityManager.createSimpleCustomEntityElement(customEntity);

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), customEntityElement.getId());
        assertNotNull(retrievedOriginalEntity);

        String code = randomString(3);
        customEntityElement.setCode(code);
        customEntityElement.setDescription("");

        api.entity().customentity().updateCustomEntityElement(customEntity.getId(), customEntityElement);
        putAsserts(customEntity, customEntityElement, retrievedOriginalEntity, code);
    }

    @Test
    public void deleteByIdElementTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);
        CustomEntityElement customEntityElement = simpleEntityManager.createSimpleCustomEntityElement(customEntity);
        Integer countBefore = api.entity().customentity().getCustomEntityElements(customEntity).getRows().size();

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), customEntityElement.getId());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().deleteCustomEntityElement(customEntity.getId(), customEntityElement.getId());

        ListEntity<CustomEntityElement> emptyElementList = api.entity().customentity().getCustomEntityElements(customEntity.getId());
        assertEquals((Integer) (countBefore - 1), emptyElementList.getMeta().getSize());
    }

    @Test
    public void deleteEntityElementTest() throws IOException, ApiClientException {
        CustomEntity customEntity = simpleEntityManager.createSimple(CustomEntity.class);
        CustomEntityElement customEntityElement = simpleEntityManager.createSimpleCustomEntityElement(customEntity);
        Integer countBefore = api.entity().customentity().getCustomEntityElements(customEntity).getRows().size();

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), customEntityElement.getId());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().deleteCustomEntityElement(customEntity.getId(), customEntityElement);

        ListEntity<CustomEntityElement> emptyElementList = api.entity().customentity().getCustomEntityElements(customEntity.getId());
        assertEquals((Integer) (countBefore - 1), emptyElementList.getMeta().getSize());
    }

    private void getAsserts(CustomEntity customEntity, CustomEntity retrievedEntity) {
        assertEquals(customEntity.getId(), retrievedEntity.getId());
        assertEquals(customEntity.getName(), retrievedEntity.getName());
        assertEquals(customEntity.getMeta().getHref(), retrievedEntity.getMeta().getHref());
        assertEquals(customEntity.getMeta().getType(), retrievedEntity.getMeta().getType());
    }

    private void putAsserts(CustomEntity customEntity, CustomEntity retrievedOriginalEntity, String name) throws IOException, ApiClientException {
        CustomEntity retrievedUpdatedEntity = getCustomEntityByHref(customEntity.getMeta().getHref());

        assertEquals(retrievedOriginalEntity.getId(), retrievedUpdatedEntity.getId());
        assertEquals(retrievedOriginalEntity.getMeta().getHref(), retrievedUpdatedEntity.getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getMeta().getType(), retrievedUpdatedEntity.getMeta().getType());
        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
    }

    private CustomEntity getCustomEntityByHref(String href) throws IOException, ApiClientException {
        List<CustomEntityMetadata> entities = api.entity().companysettings().metadata().getCustomEntities();

        if (entities == null) {
            return null;
        }

        return entities.stream().
                filter(x -> x.getEntityMeta().getMeta().getHref().equals(href)).
                findFirst().
                map(CustomEntityMetadata::getEntityMeta).
                orElse(null);
    }

    private void getAsserts(CustomEntityElement customEntity, CustomEntityElement retrievedEntity) {
        assertEquals(customEntity.getId(), retrievedEntity.getId());
        assertEquals(customEntity.getName(), retrievedEntity.getName());
        assertEquals(customEntity.getDescription(), retrievedEntity.getDescription());
        assertEquals(customEntity.getCode(), retrievedEntity.getCode());
        assertEquals(customEntity.getExternalCode(), retrievedEntity.getExternalCode());
        assertEquals(customEntity.getMeta().getHref(), retrievedEntity.getMeta().getHref());
        assertEquals(customEntity.getMeta().getType(), retrievedEntity.getMeta().getType());
    }

    private void putAsserts(CustomEntity customEntity,
                            CustomEntityElement customEntityElement,
                            CustomEntityElement retrievedOriginalEntity,
                            String code)
            throws IOException, ApiClientException {
        CustomEntityElement retrievedUpdatedEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), customEntityElement.getId());

        assertEquals(retrievedUpdatedEntity.getId(), retrievedOriginalEntity.getId());
        assertEquals(retrievedUpdatedEntity.getName(), retrievedOriginalEntity.getName());
        assertNull(retrievedUpdatedEntity.getDescription());
        assertNotEquals(retrievedOriginalEntity.getDescription(), retrievedUpdatedEntity.getDescription());
        assertNotEquals(retrievedUpdatedEntity.getCode(), retrievedOriginalEntity.getCode());
        assertEquals(code, retrievedUpdatedEntity.getCode());
        assertNull(retrievedOriginalEntity.getCode());
        assertEquals(retrievedUpdatedEntity.getExternalCode(), retrievedOriginalEntity.getExternalCode());
        assertEquals(retrievedUpdatedEntity.getMeta().getHref(), retrievedOriginalEntity.getMeta().getHref());
        assertEquals(retrievedUpdatedEntity.getMeta().getType(), retrievedOriginalEntity.getMeta().getType());
    }
}
