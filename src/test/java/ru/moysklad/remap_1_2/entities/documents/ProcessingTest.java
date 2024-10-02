package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.agents.Organization;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProcessingTest extends EntityGetUpdateDeleteTest implements FilesTest<Processing> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Processing processing = new Processing();
        processing.setName("processing_" + randomString(3) + "_" + new Date().getTime());
        processing.setMoment(LocalDateTime.now());

        ListEntity<Organization> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        processing.setOrganization(orgList.getRows().get(0));

        processing.setMaterials(new ListEntity<>());
        processing.getMaterials().setRows(new ArrayList<>());
        Product material = simpleEntityManager.createSimple(Product.class);
        DocumentPosition materialPosition = new DocumentPosition();
        materialPosition.setAssortment(material);
        materialPosition.setQuantity(3.1234);
        processing.getMaterials().getRows().add(materialPosition);

        processing.setProducts(new ListEntity<>());
        processing.getProducts().setRows(new ArrayList<>());
        Product product = simpleEntityManager.createSimple(Product.class);
        DocumentPosition productPosition = new DocumentPosition();
        productPosition.setAssortment(product);
        productPosition.setQuantity(3.1234);
        processing.getProducts().getRows().add(productPosition);

        processing.setProcessingSum(randomLong(10, 10000));
        processing.setQuantity(randomDouble(1, 5, 10));

        ListEntity<Store> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        processing.setMaterialsStore(store.getRows().get(0));
        processing.setProductsStore(store.getRows().get(0));

        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProcessingPlan.ProductItem materialItem = new ProcessingPlan.ProductItem();
        materialItem.setAssortment(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProcessingPlan.ProductItem productItem = new ProcessingPlan.ProductItem();
        productItem.setAssortment(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);
        processing.setProcessingPlan(processingPlan);

        api.entity().processing().create(processing);

        ListEntity<Processing> updatedEntitiesList = api.entity().processing().
                get(limit(50), filterEq("name", processing.getName()), expand("materials"), expand("products"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Processing retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processing.getName(), retrievedEntity.getName());
        assertEquals(processing.getMoment(), retrievedEntity.getMoment());
        assertEquals(processing.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(materialPosition.getQuantity(), retrievedEntity.getMaterials().getRows().get(0).getQuantity());
        Product retrievedMaterial = (Product) retrievedEntity.getMaterials().getRows().get(0).getAssortment();
        assertEquals(material.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        assertEquals(productPosition.getQuantity(), retrievedEntity.getProducts().getRows().get(0).getQuantity());
        Product retrievedProduct = (Product) retrievedEntity.getProducts().getRows().get(0).getAssortment();
        assertEquals(product.getMeta().getHref(), retrievedProduct.getMeta().getHref());
        assertEquals(processing.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        MetadataAttributeSharedStatesResponse<DocumentAttribute> response = api.entity().processing().metadata().get();

        assertFalse(response.getCreateShared());
    }

    @Test
    public void attributesTest() throws IOException, ApiClientException{
        ListEntity<Attribute> attributes = api.entity().processing().metadataAttributes();
        assertNotNull(attributes);
    }

    @Test
    public void createAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setType(DocumentAttribute.Type.textValue);
        String name = "field" + randomString(3) + "_" + new Date().getTime();
        attribute.setName(name);
        attribute.setRequired(false);
        attribute.setShow(true);
        attribute.setDescription("description");
        DocumentAttribute created = api.entity().processing().createMetadataAttribute(attribute);
        assertNotNull(created);
        assertEquals(name, created.getName());
        assertEquals(DocumentAttribute.Type.textValue, created.getType());
        assertFalse(created.getRequired());
        assertTrue(created.getShow());
        assertEquals("description", created.getDescription());
    }

    @Test
    public void updateAttributeTest() throws IOException, ApiClientException {
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().processing().createMetadataAttribute(attribute);

        String name = "field" + randomString(3) + "_" + new Date().getTime();
        created.setName(name);
        created.setRequired(false);
        created.setShow(false);
        DocumentAttribute updated = api.entity().processing().updateMetadataAttribute(created);
        assertNotNull(created);
        assertEquals(name, updated.getName());
        assertNull(updated.getType());
        assertEquals(Meta.Type.PRODUCT, updated.getEntityType());
        assertFalse(updated.getRequired());
        assertFalse(updated.getShow());
    }

    @Test
    public void deleteAttributeTest() throws IOException, ApiClientException{
        DocumentAttribute attribute = new DocumentAttribute();
        attribute.setEntityType(Meta.Type.PRODUCT);
        attribute.setName("field" + randomString(3) + "_" + new Date().getTime());
        attribute.setRequired(true);
        attribute.setShow(true);
        DocumentAttribute created = api.entity().processing().createMetadataAttribute(attribute);

        api.entity().processing().deleteMetadataAttribute(created);

        try {
            api.entity().processing().metadataAttributes(created.getId());
        } catch (ApiClientException e) {
            assertEquals(404, e.getStatusCode());
        }
    }

    @Ignore
    @Override
    public void massCreateDeleteTest() {
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Processing originalProcessing = (Processing) originalEntity;
        Processing retrievedProcessing = (Processing) retrievedEntity;

        assertEquals(originalProcessing.getName(), retrievedProcessing.getName());
        assertEquals(originalProcessing.getOrganization().getMeta().getHref(), retrievedProcessing.getOrganization().getMeta().getHref());
        assertEquals(originalProcessing.getMaterials().getMeta().getSize(), retrievedProcessing.getMaterials().getMeta().getSize());
        assertEquals(originalProcessing.getProducts().getMeta().getSize(), retrievedProcessing.getProducts().getMeta().getSize());
        assertEquals(originalProcessing.getProcessingPlan().getMeta().getHref(), retrievedProcessing.getProcessingPlan().getMeta().getHref());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Processing originalProcessing = (Processing) originalEntity;
        Processing updatedProcessing = (Processing) updatedEntity;

        assertNotEquals(originalProcessing.getName(), updatedProcessing.getName());
        assertEquals(changedField, updatedProcessing.getName());
        assertEquals(originalProcessing.getOrganization().getMeta().getHref(), updatedProcessing.getOrganization().getMeta().getHref());
        assertEquals(originalProcessing.getMaterials().getMeta().getSize(), updatedProcessing.getMaterials().getMeta().getSize());
        assertEquals(originalProcessing.getProducts().getMeta().getSize(), updatedProcessing.getProducts().getMeta().getSize());
        assertEquals(originalProcessing.getProcessingPlan().getMeta().getHref(), updatedProcessing.getProcessingPlan().getMeta().getHref());
    }

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().processing();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Processing.class;
    }
}
