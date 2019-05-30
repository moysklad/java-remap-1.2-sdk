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
        ProcessingPlanDocumentEntity e = new ProcessingPlanDocumentEntity();
        e.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        e.setMaterials(new ListEntity<>());
        e.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = new ProductEntity();
        material.setName(randomString());
        api.entity().product().post(material);
        PlanItem materialItem = new PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        e.getMaterials().getRows().add(materialItem);

        e.setProducts(new ListEntity<>());
        e.getProducts().setRows(new ArrayList<>());
        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        PlanItem productItem = new PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        e.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(e);

        ListEntity<ProcessingPlanDocumentEntity> updatedEntitiesList = api.entity().processingplan().
                get(limit(50), filterEq("name", e.getName()), expand("materials"), expand("products"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingPlanDocumentEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMoment(), retrievedEntity.getMoment());
        assertEquals(materialItem.getQuantity(), retrievedEntity.getMaterials().getRows().get(0).getQuantity());
        ProductEntity retrievedMaterial = (ProductEntity) retrievedEntity.getMaterials().getRows().get(0).getProduct();
        assertEquals(material.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        assertEquals(productItem.getQuantity(), retrievedEntity.getProducts().getRows().get(0).getQuantity());
        ProductEntity retrievedProduct = (ProductEntity) retrievedEntity.getProducts().getRows().get(0).getProduct();
        assertEquals(product.getMeta().getHref(), retrievedProduct.getMeta().getHref());
    }

    @Test
    public void getTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity e = createSimpleDocumentProcessingPlan();

        ProcessingPlanDocumentEntity retrievedEntity = api.entity().processingplan().get(e.getId());
        getAsserts(e, retrievedEntity);

        retrievedEntity = api.entity().processingplan().get(e);
        getAsserts(e, retrievedEntity);
    }

    @Test
    public void putTest() throws IOException, LognexApiException, InterruptedException {
        ProcessingPlanDocumentEntity e = createSimpleDocumentProcessingPlan();

        ProcessingPlanDocumentEntity retrievedOriginalEntity = api.entity().processingplan().get(e.getId());
        String name = "processingplan_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().processingplan().put(e.getId(), e);
        putAsserts(e, retrievedOriginalEntity, name);

        retrievedOriginalEntity.set(e);

        name = "processingplan_" + randomString(3) + "_" + new Date().getTime();
        e.setName(name);
        api.entity().processingplan().put(e);
        putAsserts(e, retrievedOriginalEntity, name);
    }

    @Test
    public void deleteTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity e = createSimpleDocumentProcessingPlan();

        ListEntity<ProcessingPlanDocumentEntity> entitiesList = api.entity().processingplan().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processingplan().delete(e.getId());

        entitiesList = api.entity().processingplan().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    @Test
    public void deleteByIdTest() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity e = createSimpleDocumentProcessingPlan();

        ListEntity<ProcessingPlanDocumentEntity> entitiesList = api.entity().processingplan().get(filterEq("name", e.getName()));
        assertEquals((Integer) 1, entitiesList.getMeta().getSize());

        api.entity().processingplan().delete(e);

        entitiesList = api.entity().processingplan().get(filterEq("name", e.getName()));
        assertEquals((Integer) 0, entitiesList.getMeta().getSize());
    }

    private ProcessingPlanDocumentEntity createSimpleDocumentProcessingPlan() throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity e = new ProcessingPlanDocumentEntity();
        e.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        e.setMaterials(new ListEntity<>());
        e.getMaterials().setRows(new ArrayList<>());
        ProductEntity material = new ProductEntity();
        material.setName(randomString());
        api.entity().product().post(material);
        PlanItem materialItem = new PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        e.getMaterials().getRows().add(materialItem);

        e.setProducts(new ListEntity<>());
        e.getProducts().setRows(new ArrayList<>());
        ProductEntity product = new ProductEntity();
        product.setName(randomString());
        api.entity().product().post(product);
        PlanItem productItem = new PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        e.getProducts().getRows().add(productItem);

        api.entity().processingplan().post(e);

        return e;
    }

    private void getAsserts(ProcessingPlanDocumentEntity e, ProcessingPlanDocumentEntity retrievedEntity) {
        assertEquals(e.getName(), retrievedEntity.getName());
        assertEquals(e.getMaterials().getMeta().getSize(), retrievedEntity.getMaterials().getMeta().getSize());
        assertEquals(e.getProducts().getMeta().getSize(), retrievedEntity.getProducts().getMeta().getSize());
    }

    private void putAsserts(ProcessingPlanDocumentEntity e, ProcessingPlanDocumentEntity retrievedOriginalEntity, String name) throws IOException, LognexApiException {
        ProcessingPlanDocumentEntity retrievedUpdatedEntity = api.entity().processingplan().get(e.getId());

        assertNotEquals(retrievedOriginalEntity.getName(), retrievedUpdatedEntity.getName());
        assertEquals(name, retrievedUpdatedEntity.getName());
        assertEquals(retrievedOriginalEntity.getMaterials().getMeta().getSize(), retrievedUpdatedEntity.getMaterials().getMeta().getSize());
        assertEquals(retrievedOriginalEntity.getProducts().getMeta().getSize(), retrievedUpdatedEntity.getProducts().getMeta().getSize());
    }
}
