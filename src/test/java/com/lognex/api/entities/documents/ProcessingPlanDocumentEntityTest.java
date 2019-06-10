package com.lognex.api.entities.documents;

import com.lognex.api.entities.EntityTestBase;
import com.lognex.api.entities.documents.ProcessingPlanDocumentEntity.PlanItem;
import com.lognex.api.entities.products.ProductEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProcessingPlanDocumentEntityTest extends EntityTestBase {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity processingPlan = new ProcessingPlanDocumentEntity();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = simpleEntityFactory.createSimpleProduct();
        PlanItem materialItem = new PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        ProductEntity product = simpleEntityFactory.createSimpleProduct();
        PlanItem productItem = new PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(processingPlan);

        ListEntity<ProcessingPlanDocumentEntity> updatedEntitiesList = api.entity().processingplan().
                get(limit(50), filterEq("name", processingPlan.getName()), expand("materials"), expand("products"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingPlanDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processingPlan.getName(), retrievedEntity.getName());
        assertEquals(processingPlan.getMoment(), retrievedEntity.getMoment());
        assertEquals(materialItem.getQuantity(), retrievedEntity.getMaterials().getRows().get(0).getQuantity());
        ProductEntity retrievedMaterial = (ProductEntity) retrievedEntity.getMaterials().getRows().get(0).getProduct();
        assertEquals(material.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        assertEquals(productItem.getQuantity(), retrievedEntity.getProducts().getRows().get(0).getQuantity());
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity.getProducts().getRows().get(0).getProduct();
        assertEquals(product.getMeta().getHref(), retrievedProduct.getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity processingPlan = simpleEntityFactory.createSimpleProcessingPlan();

        ProcessingPlanDocumentEntity retrievedEntity = api.entity().processingplan().get(processingPlan.getId());
        getAsserts(processingPlan, retrievedEntity);

        retrievedEntity = api.entity().processingplan().get(processingPlan);
        getAsserts(processingPlan, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity processingPlan = simpleEntityFactory.createSimpleProcessingPlan();

        ProcessingPlanDocumentEntity retrievedOriginalEntity = api.entity().processingplan().get(processingPlan.getId());
        String name = "processingplan_" + randomString(3) + "_" + new Date().getTime();
        processingPlan.setName(name);
        api.entity().processingplan().put(processingPlan.getId(), processingPlan);
        putAsserts(processingPlan, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(processingPlan);

        name = "processingplan_" + randomString(3) + "_" + new Date().getTime();
        processingPlan.setName(name);
        api.entity().processingplan().put(processingPlan);
        putAsserts(processingPlan, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity processingPlan = simpleEntityFactory.createSimpleProcessingPlan();

        ListEntity<ProcessingPlanDocumentEntity> entitiesList = api.entity().processingplan().get(filterEq("name", processingPlan.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processingplan().delete(processingPlan.getId());

        entitiesList = api.entity().processingplan().get(filterEq("name", processingPlan.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity processingPlan = simpleEntityFactory.createSimpleProcessingPlan();

        ListEntity<ProcessingPlanDocumentEntity> entitiesList = api.entity().processingplan().get(filterEq("name", processingPlan.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processingplan().delete(processingPlan);

        entitiesList = api.entity().processingplan().get(filterEq("name", processingPlan.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private void getAsserts(ProcessingPlanDocumentEntity processingPlan, ProcessingPlanDocumentEntity retrievedEntity) {
        assertEquals(processingPlan.getName(), retrievedEntity.getName());
        assertEquals(processingPlan.getMaterials().getMeta().getSize(), retrievedEntity.getMaterials().getMeta().getSize());
        assertEquals(processingPlan.getProducts().getMeta().getSize(), retrievedEntity.getProducts().getMeta().getSize());
    }

    private void putAsserts(ProcessingPlanDocumentEntity processingPlan, ProcessingPlanDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity retrievedUpdatedEntity = api.entity().processingplan().get(processingPlan.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getMaterials().getMeta().getSize(), retrievedUpdatedEntity.getMaterials().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getProducts().getMeta().getSize(), retrievedUpdatedEntity.getProducts().getMeta().getSize());
    }
}
