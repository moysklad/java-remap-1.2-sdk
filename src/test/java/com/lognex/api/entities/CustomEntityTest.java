package com.lognex.api.entities;

import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.CompanySettingsMetadata.CustomEntityMetadata;
import com.lognex.api.utils.LognexApiException;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class CustomEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        CustomEntity e = new CustomEntity();
        e.setName("custom_entity_" + randomString(3) + "_" + new Date().getTime());

        api.entity().customentity().post(e);

        CustomEntity retrievedEntity = getCustomEntityByHref(e.getMeta().getHref());

        assertNotNull(retrievedEntity);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putByIdTest() throws IOException, LognexApiException, InterruptedException {
        CustomEntity e = createSimpleCustom();

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(e.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        String name = "custom_entity_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);

        Thread.sleep(1500);
        api.entity().customentity().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void putEntityTest() throws IOException, LognexApiException, InterruptedException {
        CustomEntity e = createSimpleCustom();

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(e.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        String name = "custom_entity_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);

        Thread.sleep(1500);
        api.entity().customentity().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        CustomEntity e = createSimpleCustom();

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(e.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().delete(e.getId());

        CustomEntity retrievedNullEntity = getCustomEntityByHref(e.getMeta().getHref());
        assertNull(retrievedNullEntity);
    }

    @Test
    public void deleteEntityTest() throws IOException, LognexApiException {
        CustomEntity e = createSimpleCustom();

        CustomEntity retrievedOriginalEntity = getCustomEntityByHref(e.getMeta().getHref());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().delete(e);

        CustomEntity retrievedNullEntity = getCustomEntityByHref(e.getMeta().getHref());
        assertNull(retrievedNullEntity);
    }

    @Test
    @SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
    public void errorTest() throws IOException {
        try {
            CustomEntity e = new CustomEntity();
            api.entity().customentity().post(e);
            fail("Ожидалось исключение");
        } catch (LognexApiException e) {
            assertApiError(
                    e, 412,
                    Arrays.asList(
                            Pair.of(3000, "Ошибка сохранения объекта: поле 'name' не может быть пустым или отсутствовать")
                    )
            );
        }
    }

    @Test
    public void postElementTest() throws IOException, LognexApiException {
        CustomEntity customEntity = createSimpleCustom();
        CustomEntityElement e = new CustomEntityElement();
        e.setName("custom_entity_element_" + randomString(3) + "_" + new Date().getTime());

        api.entity().customentity().postCustomEntityElement(customEntity.getId(), e);

        CustomEntityElement retrievedEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), e.getId());

        assertNotNull(retrievedEntity);
        assertEquals(e.getId(), retrievedEntity.getId());
        assertEquals(e.getName(), retrievedEntity.getName());
    }

    @Test
    public void getElementTest() throws IOException, LognexApiException {
        CustomEntity customEntity = createSimpleCustom();
        CustomEntityElement e = createSimpleCustomElement(customEntity);

        CustomEntityElement retrievedEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), e.getId());

        getAsserts(e, retrievedEntity);
    }

    @Test
    public void getByIdElementsTest() throws IOException, LognexApiException {
        CustomEntity customEntity = createSimpleCustom();

        List<CustomEntityElement> customEntityElementList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customEntityElementList.add(createSimpleCustomElement(customEntity));
        }

        List<CustomEntityElement> retrievedEntities = api.entity().customentity().getCustomEntityElements(customEntity.getId()).getRows();

        assertEquals(3, retrievedEntities.size());

        for (int i = 0; i < 3; i++) {
            getAsserts(customEntityElementList.get(i), retrievedEntities.get(i));
        }
    }

    @Test
    public void getEntityElementsTest() throws IOException, LognexApiException {
        CustomEntity customEntity = createSimpleCustom();

        List<CustomEntityElement> customEntityElementList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            customEntityElementList.add(createSimpleCustomElement(customEntity));
        }

        List<CustomEntityElement> retrievedEntities = api.entity().customentity().getCustomEntityElements(customEntity).getRows();

        assertEquals(3, retrievedEntities.size());

        for (int i = 0; i < 3; i++) {
            getAsserts(customEntityElementList.get(i), retrievedEntities.get(i));
        }
    }

    @Test
    public void putByIdElementTest() throws IOException, LognexApiException, InterruptedException {
        CustomEntity customEntity = createSimpleCustom();
        CustomEntityElement e = createSimpleCustomElement(customEntity);

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), e.getId());
        assertNotNull(retrievedOriginalEntity);

        String code = randomString(3);
        e.setCode(code);
        e.setDescription("");

        Thread.sleep(1500);
        api.entity().customentity().putCustomEntityElement(customEntity.getId(), e.getId(), e);
        putAsserts(customEntity, e, retrievedOriginalEntity, code);
    }

    @Test
    public void putEntityElementTest() throws IOException, LognexApiException, InterruptedException {
        CustomEntity customEntity = createSimpleCustom();
        CustomEntityElement e = createSimpleCustomElement(customEntity);

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), e.getId());
        assertNotNull(retrievedOriginalEntity);

        String code = randomString(3);
        e.setCode(code);
        e.setDescription("");

        Thread.sleep(1500);
        api.entity().customentity().putCustomEntityElement(customEntity.getId(), e);
        putAsserts(customEntity, e, retrievedOriginalEntity, code);
    }

    @Test
    public void deleteByIdElementTest() throws IOException, LognexApiException {
        CustomEntity customEntity = createSimpleCustom();
        CustomEntityElement e = createSimpleCustomElement(customEntity);

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), e.getId());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().deleteCustomEntityElement(customEntity.getId(), e.getId());

        ListEntity<CustomEntityElement> emptyElementList = api.entity().customentity().getCustomEntityElements(customEntity.getId());
        assertEquals(0, emptyElementList.getRows().size());
    }

    @Test
    public void deleteEntityElementTest() throws IOException, LognexApiException {
        CustomEntity customEntity = createSimpleCustom();
        CustomEntityElement e = createSimpleCustomElement(customEntity);

        CustomEntityElement retrievedOriginalEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), e.getId());
        assertNotNull(retrievedOriginalEntity);

        api.entity().customentity().deleteCustomEntityElement(customEntity.getId(), e);

        ListEntity<CustomEntityElement> emptyElementList = api.entity().customentity().getCustomEntityElements(customEntity.getId());
        assertEquals(0, emptyElementList.getRows().size());
    }


    private void getAsserts(CustomEntity e, CustomEntity retrievedEntity) {
        assertEquals(e.getId(), retrievedEntity.getId());
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMeta().getHref(), retrievedEntity.getMeta().getHref());
        assertEquals(e.getMeta().getType(), retrievedEntity.getMeta().getType());
    }

    private void putAsserts(CustomEntity e, CustomEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        CustomEntity retrievedUpdatedEntity = getCustomEntityByHref(e.getMeta().getHref());

        assertEquals(retrievedOriginalEntity.getId(), retrievedUpdatedEntity.getId());
        assertEquals(retrievedOriginalEntity.getMeta().getHref(), retrievedUpdatedEntity.getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getMeta().getType(), retrievedUpdatedEntity.getMeta().getType());
        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
    }

    private CustomEntity createSimpleCustom() throws IOException, LognexApiException {
        CustomEntity e = new CustomEntity();
        
        e.setName("custom_entity_" + randomString(3) + "_" + new Date().getTime());

        api.entity().customentity().post(e);
        return e;
    }

    private CustomEntity getCustomEntityByHref(String href) throws IOException, LognexApiException{
        List<CustomEntityMetadata> entities = api.entity().companysettings().metadata().getCustomEntities();

        return entities.stream().
                filter(x -> x.getEntityMeta().getMeta().getHref().equals(href)).
                findFirst().
                map(CustomEntityMetadata::getEntityMeta).
                orElse(null);
    }

    private CustomEntityElement createSimpleCustomElement(CustomEntity customEntity) throws IOException, LognexApiException {
        CustomEntityElement e = new CustomEntityElement();

        e.setName("custom_entity_element_" + randomString(3) + "_" + new Date().getTime());
        e.setDescription("custom_entity_desc_" + randomString(3));
        e.setExternalCode(randomString(3));

        api.entity().customentity().postCustomEntityElement(customEntity.getId(), e);
        return e;
    }

    private void getAsserts(CustomEntityElement e, CustomEntityElement retrievedEntity) {
        assertEquals(e.getId(), retrievedEntity.getId());
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getDescription(), retrievedEntity.getDescription());
        assertEquals(e.getCode(), retrievedEntity.getCode());
        assertEquals(e.getExternalCode(), retrievedEntity.getExternalCode());
        assertEquals(e.getMeta().getHref(), retrievedEntity.getMeta().getHref());
        assertEquals(e.getMeta().getType(), retrievedEntity.getMeta().getType());
    }

    private void putAsserts(CustomEntity customEntity,
                            CustomEntityElement e,
                            CustomEntityElement retrievedOriginalEntity,
                            String code)
            throws IOException, LognexApiException {
        CustomEntityElement retrievedUpdatedEntity = api.entity().customentity().getCustomEntityElement(customEntity.getId(), e.getId());

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
