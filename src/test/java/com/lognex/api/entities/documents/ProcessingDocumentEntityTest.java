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
        ProcessingDocumentEntity processing = new ProcessingDocumentEntity();
        processing.setName("processing_" + randomString(3) + "_" + new Date().getTime());
        processing.setMoment(LocalDateTime.now());

        ListEntity<OrganizationEntity> orgList = api.entity().organization().get();
        assertNotEquals(0, orgList.getRows().size());
        processing.setOrganization(orgList.getRows().get(0));

        processing.setMaterials(new ListEntity<>());
        processing.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = simpleEntityFactory.createSimpleProduct();
        DocumentPosition materialPosition = new DocumentPosition();
        materialPosition.setAssortment(material);
        materialPosition.setQuantity(3.1234);
        processing.getMaterials().getRows().add(materialPosition);

        processing.setProducts(new ListEntity<>());
        processing.getProducts().setRows(new ArrayList<>());
        ProductEntity product = simpleEntityFactory.createSimpleProduct();
        DocumentPosition productPosition = new DocumentPosition();
        productPosition.setAssortment(product);
        productPosition.setQuantity(3.1234);
        processing.getProducts().getRows().add(productPosition);

        processing.setProcessingSum(randomLong(10, 10000));
        processing.setQuantity(randomDouble(1, 5, 10));

        ListEntity<StoreEntity> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        processing.setMaterialsStore(store.getRows().get(0));
        processing.setProductsStore(store.getRows().get(0));

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
        processing.setProcessingPlan(processingPlan);

        api.entity().processing().post(processing);

        ListEntity<ProcessingDocumentEntity> updatedEntitiesList = api.entity().processing().
                get(limit(50), filterEq("name", processing.getName()), expand("materials"), expand("products"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processing.getName(), retrievedEntity.getName());
        assertEquals(processing.getMoment(), retrievedEntity.getMoment());
        assertEquals(processing.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(materialPosition.getQuantity(), retrievedEntity.getMaterials().getRows().get(0).getQuantity());
        ProductEntity retrievedMaterial = (ProductEntity) retrievedEntity.getMaterials().getRows().get(0).getAssortment();
        assertEquals(material.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        assertEquals(productPosition.getQuantity(), retrievedEntity.getProducts().getRows().get(0).getQuantity());
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity.getProducts().getRows().get(0).getAssortment();
        assertEquals(product.getMeta().getHref(), retrievedProduct.getMeta().getHref());
        assertEquals(processing.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity processing = simpleEntityFactory.createSimpleProcessing();

        ProcessingDocumentEntity retrievedEntity = api.entity().processing().get(processing.getId());
        getAsserts(processing, retrievedEntity);

        retrievedEntity = api.entity().processing().get(processing);
        getAsserts(processing, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity processing = simpleEntityFactory.createSimpleProcessing();

        ProcessingDocumentEntity retrievedOriginalEntity = api.entity().processing().get(processing.getId());
        String name = "processing_" + randomString(3) + "_" + new Date().getTime();
        processing.setName(name);
        api.entity().processing().put(processing.getId(), processing);
        putAsserts(processing, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(processing);

        name = "processing_" + randomString(3) + "_" + new Date().getTime();
        processing.setName(name);
        api.entity().processing().put(processing);
        putAsserts(processing, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity processing = simpleEntityFactory.createSimpleProcessing();

        ListEntity<ProcessingDocumentEntity> entitiesList = api.entity().processing().get(filterEq("name", processing.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processing().delete(processing.getId());

        entitiesList = api.entity().processing().get(filterEq("name", processing.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProcessingDocumentEntity processing = simpleEntityFactory.createSimpleProcessing();

        ListEntity<ProcessingDocumentEntity> entitiesList = api.entity().processing().get(filterEq("name", processing.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processing().delete(processing);

        entitiesList = api.entity().processing().get(filterEq("name", processing.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void metadataTest() throws IOException, LognexApiException {
        MetadataAttributeSharedStatesResponse response = api.entity().processing().metadata().get();

        assertFalse(response.getCreateShared());
    }

    private void getAsserts(ProcessingDocumentEntity processing, ProcessingDocumentEntity retrievedEntity) {
        assertEquals(processing.getName(), retrievedEntity.getName());
        assertEquals(processing.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(processing.getMaterials().getMeta().getSize(), retrievedEntity.getMaterials().getMeta().getSize());
        assertEquals(processing.getProducts().getMeta().getSize(), retrievedEntity.getProducts().getMeta().getSize());
        assertEquals(processing.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
    }

    private void putAsserts(ProcessingDocumentEntity processing, ProcessingDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProcessingDocumentEntity retrievedUpdatedEntity = api.entity().processing().get(processing.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getOrganization().getMeta().getHref(), retrievedUpdatedEntity.getOrganization().getMeta().getHref());
        assertEquals(retrievedOriginalEntity.getMaterials().getMeta().getSize(), retrievedUpdatedEntity.getMaterials().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getProducts().getMeta().getSize(), retrievedUpdatedEntity.getProducts().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getProcessingPlan().getMeta().getHref(), retrievedUpdatedEntity.getProcessingPlan().getMeta().getHref());
    }
}
