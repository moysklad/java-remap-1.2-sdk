package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.products.*;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static org.junit.Assert.*;

public class ServiceTest extends EntityGetUpdateDeleteTest implements FilesTest<Service> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Service service = new Service();
        service.setName("service_" + randomString(3) + "_" + new Date().getTime());
        service.setArchived(false);
        service.setDescription(randomString());
        Price minPrice = new Price();
        minPrice.setValue(22.2345);
        minPrice.setCurrency(simpleEntityManager.getFirstCurrency());
        service.setMinPrice(minPrice);
        service.setPaymentItemType(ServicePaymentItemType.WORK);
        service.setTaxSystem(GoodTaxSystem.GENERAL_TAX_SYSTEM);

        api.entity().service().create(service);

        ListEntity<Service> updatedEntitiesList = api.entity().service().get(filterEq("name", service.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Service retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(service.getName(), retrievedEntity.getName());
        assertEquals(service.getArchived(), retrievedEntity.getArchived());
        assertEquals(service.getDescription(), retrievedEntity.getDescription());
        assertEquals(service.getMinPrice().getValue(), retrievedEntity.getMinPrice().getValue());
        assertEquals(service.getMinPrice().getCurrency(), retrievedEntity.getMinPrice().getCurrency());
        assertEquals(service.getPaymentItemType(), retrievedEntity.getPaymentItemType());
        assertEquals(service.getTaxSystem(), retrievedEntity.getTaxSystem());
    }

    @Test
    public void createDiscountProhibitedService() throws ApiClientException, IOException {
        Service serviceDiscountProhibited = new Service();
        serviceDiscountProhibited.setName("service_" + randomString(3) + "_" + new Date().getTime());
        serviceDiscountProhibited.setArchived(false);
        serviceDiscountProhibited.setDescription(randomString());
        Price minPrice = new Price();
        minPrice.setValue(22.2345);
        minPrice.setCurrency(simpleEntityManager.getFirstCurrency());
        serviceDiscountProhibited.setMinPrice(minPrice);
        serviceDiscountProhibited.setPaymentItemType(ServicePaymentItemType.WORK);
        serviceDiscountProhibited.setTaxSystem(GoodTaxSystem.GENERAL_TAX_SYSTEM);
        serviceDiscountProhibited.setDiscountProhibited(true);

        api.entity().service().create(serviceDiscountProhibited);
        ListEntity<Service> updatedEntitiesList = api.entity().service()
                .get(filterEq("name", serviceDiscountProhibited.getName()));
        assertEquals(1, updatedEntitiesList.getRows().size());
        Service retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(serviceDiscountProhibited.getDiscountProhibited(), retrievedEntity.getDiscountProhibited());

        Service serviceDiscountNotProhibited = new Service();
        serviceDiscountNotProhibited.setName("service_" + randomString(3) + "_" + new Date().getTime());
        serviceDiscountNotProhibited.setArchived(false);
        serviceDiscountNotProhibited.setDescription(randomString());
        Price minPrice2 = new Price();
        minPrice2.setValue(22.2345);
        minPrice2.setCurrency(simpleEntityManager.getFirstCurrency());
        serviceDiscountNotProhibited.setMinPrice(minPrice2);
        serviceDiscountNotProhibited.setPaymentItemType(ServicePaymentItemType.WORK);
        serviceDiscountNotProhibited.setTaxSystem(GoodTaxSystem.GENERAL_TAX_SYSTEM);
        serviceDiscountNotProhibited.setDiscountProhibited(false);

        api.entity().service().create(serviceDiscountNotProhibited);
        ListEntity<Service> updatedEntitiesList2 = api.entity().service()
                .get(filterEq("name", serviceDiscountNotProhibited.getName()));
        assertEquals(1, updatedEntitiesList2.getRows().size());
        Service retrievedEntity2 = updatedEntitiesList2.getRows().get(0);
        assertEquals(serviceDiscountNotProhibited.getDiscountProhibited(), retrievedEntity2.getDiscountProhibited());
    }

    @Test
    public void paymentItemTypeTest() {
        Service service = simpleEntityManager.createSimple(Service.class);

        Arrays.stream(ServicePaymentItemType.values()).forEach(servicePaymentItemType -> {
            service.setPaymentItemType(servicePaymentItemType);
            try {
                api.entity().service().update(service);
                assertEquals(servicePaymentItemType, service.getPaymentItemType());
            } catch (IOException | ApiClientException e) {
                fail();
            }
        });
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedResponse metadata = api.entity().service().metadata();
        assertTrue(metadata.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().service().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setType(Attribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        Attribute created = api.entity().service().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(Attribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        Attribute attribute = new Attribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        Attribute created = api.entity().service().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        attribute.setShow(false);
        Attribute updated = api.entity().service().updateMetadataAttribute(created);
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
        attribute.setShow(true);
        Attribute created = api.entity().service().createMetadataAttribute(attribute);

        api.entity().service().deleteMetadataAttribute(created);

        try {
            api.entity().service().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Service originalService = (Service) originalEntity;
        Service retrievedService = (Service) retrievedEntity;

        assertEquals(originalService.getName(), retrievedService.getName());
        assertEquals(originalService.getDescription(), retrievedService.getDescription());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Service originalService = (Service) originalEntity;
        Service updatedService = (Service) updatedEntity;

        assertNotEquals(originalService.getName(), updatedService.getName());
        assertEquals(changedField, updatedService.getName());
        assertEquals(originalService.getDescription(), updatedService.getDescription());
    }

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().service();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Service.class;
    }
}
