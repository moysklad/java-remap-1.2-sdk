package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.StoreEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProcessingDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity e = new ProcessingDocumentEntity();
        e.setName("processing_" + randomString(3) + "_" + new Date().getTime());
        e.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        e.setMaterials(new ListEntity<>());
        e.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = new ProductEntity();
        material.setName(randomString());
        api.entity().product().post(material);
        DocumentPosition materialPosition = new DocumentPosition();
        materialPosition.setAssortment(material);
        materialPosition.setQuantity(3.1234);
        e.getMaterials().getRows().add(materialPosition);

        e.setProducts(new ListEntity<>());
        e.getProducts().setRows(new ArrayList<>());
        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        DocumentPosition productPosition = new DocumentPosition();
        productPosition.setAssortment(product);
        productPosition.setQuantity(3.1234);
        e.getProducts().getRows().add(productPosition);

        e.setProcessingSum(randomLong(10, 10000));
        e.setQuantity(randomDouble(1, 5, 10));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setMaterialsStore(store.getRows().get(0));
        e.setProductsStore(store.getRows().get(0));

        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);
        e.setProcessingPlan(processingPlan);

        api.entity().processing().post(e);

        ListEntity<ProcessingDocumentEntity> updatedEntitiesList = api.entity().processing().
                get(limit(50), filterEq("name", e.getName()), expand("materials"), expand("products"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(materialPosition.getQuantity(), retrievedEntity.getMaterials().getRows().get(0).getQuantity());
        ProductEntity retrievedMaterial = (ProductEntity) retrievedEntity.getMaterials().getRows().get(0).getAssortment();
        assertEquals(material.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        assertEquals(productPosition.getQuantity(), retrievedEntity.getProducts().getRows().get(0).getQuantity());
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity.getProducts().getRows().get(0).getAssortment();
        assertEquals(product.getMeta().getHref(), retrievedProduct.getMeta().getHref());
        assertEquals(e.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity e = createSimpleDocumentProcessing();

        ProcessingDocumentEntity retrievedEntity = api.entity().processing().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().processing().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ProcessingDocumentEntity e = createSimpleDocumentProcessing();

        ProcessingDocumentEntity retrievedOriginalEntity = api.entity().processing().get(e.getId());
        String name = "processing_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().processing().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "processing_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().processing().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity e = createSimpleDocumentProcessing();

        ListEntity<ProcessingDocumentEntity> entitiesList = api.entity().processing().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processing().delete(e.getId());

        entitiesList = api.entity().processing().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity e = createSimpleDocumentProcessing();

        ListEntity<ProcessingDocumentEntity> entitiesList = api.entity().processing().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processing().delete(e);

        entitiesList = api.entity().processing().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().processing().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private ProcessingDocumentEntity createSimpleDocumentProcessing() throws IOException, LognexApiException {
        ProcessingDocumentEntity e = new ProcessingDocumentEntity();
        e.setName("processing_" + randomString(3) + "_" + new Date().getTime());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        e.setOrganization(orgList.getRows().get(0));

        e.setMaterials(new ListEntity<>());
        e.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = new ProductEntity();
        material.setName(randomString());
        api.entity().product().post(material);
        DocumentPosition materialPosition = new DocumentPosition();
        materialPosition.setAssortment(material);
        materialPosition.setQuantity(randomDouble(1, 5, 10));
        e.getMaterials().getRows().add(materialPosition);

        e.setProducts(new ListEntity<>());
        e.getProducts().setRows(new ArrayList<>());
        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        DocumentPosition productPosition = new DocumentPosition();
        productPosition.setAssortment(product);
        productPosition.setQuantity(randomDouble(1, 5, 10));
        e.getProducts().getRows().add(productPosition);

        e.setProcessingSum(randomLong(10, 10000));
        e.setQuantity(randomDouble(1, 5, 10));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        e.setMaterialsStore(store.getRows().get(0));
        e.setProductsStore(store.getRows().get(0));

        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProcessingPlanDocumentEntity.PlanItem materialItem = new ProcessingPlanDocumentEntity.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProcessingPlanDocumentEntity.PlanItem productItem = new ProcessingPlanDocumentEntity.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);
        e.setProcessingPlan(processingPlan);

        api.entity().processing().post(e);

        return e;
    }

    private void getAsserts(ProcessingDocumentEntity e, ProcessingDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(e.getMaterials().getMeta().getSize(), retrievedEntity.getMaterials().getMeta().getSize());
        assertEquals(e.getProducts().getMeta().getSize(), retrievedEntity.getProducts().getMeta().getSize());
        assertEquals(e.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
    }

    private void putAsserts(ProcessingDocumentEntity e, ProcessingDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProcessingDocumentEntity retrievedUpdatedEntity = api.entity().processing().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getMaterials().getMeta().getSize(), retrievedUpdatedEntity.getMaterials().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getProducts().getMeta().getSize(), retrievedUpdatedEntity.getProducts().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getProcessingPlan().getMeta().getHref(), retrievedUpdatedEntity.getProcessingPlan().getMeta().getHref());
    }
}
