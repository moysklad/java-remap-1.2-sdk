package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.TrackingType;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.*;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;

public class EmissionOrderTest extends DocumentWithPositionsTestBase {

    @Override
    public void tearDown() {
        super.tearDown();
        simpleEntityManager.removeSimpleFromPool(EmissionOrder.class);
    }

    @Test
    public void createTest() throws IOException, ApiClientException {
        EmissionOrder emissionOrder = new EmissionOrder();
        emissionOrder.setName("emissionorder" + randomString(3) + "_" + new Date().getTime());
        emissionOrder.setDescription(randomString());
        emissionOrder.setEmissionType(EmissionOrder.EmissionType.LOCAL);
        emissionOrder.setTrackingType(TrackingType.SHOES);
        emissionOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        api.entity().emissionorder().create(emissionOrder);

        ListEntity<EmissionOrder> updatedEntitiesList = api.entity().emissionorder().get(filterEq("name", emissionOrder.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        EmissionOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(emissionOrder.getName(), retrievedEntity.getName());
        assertEquals(emissionOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(emissionOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(emissionOrder.getEmissionType(), EmissionOrder.EmissionType.LOCAL);
        assertEquals(emissionOrder.getTrackingType(), TrackingType.SHOES);
        assertEquals(emissionOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
    }

    @Test
    @Ignore("Причина - удаление документа не поддерживается через json api")
    @Override
    public void massCreateDeleteTest() {}

    @Test
    @Ignore("Причина - удаление документа не поддерживается через json api")
    @Override
    public void deleteTest() throws IOException, ApiClientException {}

    @Test
    @Ignore("Причина - файлы не поддерживаюся через json api для данного документа")
    @Override
    public void testFiles() throws IOException, ApiClientException {}

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().emissionorder().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        EmissionOrder original = (EmissionOrder) originalEntity;
        EmissionOrder retrieved = (EmissionOrder) retrievedEntity;

        assertEquals(original.getName(), retrieved.getName());
        assertEquals(original.getDescription(), retrieved.getDescription());
        assertEquals(original.getSum(), retrieved.getSum());
        assertEquals(original.getMoment(), retrieved.getMoment());
        assertEquals(original.getEmissionType(), retrieved.getEmissionType());
        assertEquals(original.getTrackingType(), retrieved.getTrackingType());
        assertEquals(original.getOrganization().getMeta().getHref(), retrieved.getOrganization().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        EmissionOrder original = (EmissionOrder) originalEntity;
        EmissionOrder updated = (EmissionOrder) updatedEntity;

        assertNotEquals(original.getName(), updated.getName());
        assertEquals(changedField, updated.getName());
        assertEquals(original.getDescription(), updated.getDescription());
        assertEquals(original.getSum(), updated.getSum());
        assertEquals(original.getMoment(), updated.getMoment());
        assertEquals(original.getOrganization().getMeta().getHref(), updated.getOrganization().getMeta().getHref());
        assertEquals(original.getEmissionType(), updated.getEmissionType());
        assertEquals(original.getTrackingType(), updated.getTrackingType());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().emissionorder();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return EmissionOrder.class;
    }

    @Override
    protected double getQuantityValueForPosition(DocumentPosition position) {
        double v = position.getQuantity() == null ? 0 : position.getQuantity();
        return v + randomInteger(1, 2);
    }

    @Override
    protected Product getProductValueForPosition(DocumentPosition position) {
        Product product = new Product();
        product.setName("product_" + randomString(3) + "_" + new Date().getTime());
        product.setTrackingType(TrackingType.SHOES);
        try {
            return api.entity().product().create(product);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
