package com.lognex.api.entities.documents;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.documents.ProcessingPlan.PlanItem;
import com.lognex.api.entities.products.Product;
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

public class ProcessingPlanTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        Product material = simpleEntityManager.createSimple(Product.class);
        PlanItem materialItem = new PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        Product product = simpleEntityManager.createSimple(Product.class);
        PlanItem productItem = new PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);

        ListEntity<ProcessingPlan> updatedEntitiesList = api.entity().processingplan().
                get(limit(50), filterEq("name", processingPlan.getName()), expand("materials"), expand("products"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingPlan retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processingPlan.getName(), retrievedEntity.getName());
        assertEquals(processingPlan.getMoment(), retrievedEntity.getMoment());
        assertEquals(materialItem.getQuantity(), retrievedEntity.getMaterials().getRows().get(0).getQuantity());
        Product retrievedMaterial = (Product) retrievedEntity.getMaterials().getRows().get(0).getProduct();
        assertEquals(material.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        assertEquals(productItem.getQuantity(), retrievedEntity.getProducts().getRows().get(0).getQuantity());
        Product retrievedProduct = (Product) retrievedEntity.getProducts().getRows().get(0).getProduct();
        assertEquals(product.getMeta().getHref(), retrievedProduct.getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ProcessingPlan originalProcessingPlan = (ProcessingPlan) originalEntity;
        ProcessingPlan retrievedProcessingPlan = (ProcessingPlan) retrievedEntity;

        assertEquals(originalProcessingPlan.getName(), retrievedProcessingPlan.getName());
        assertEquals(originalProcessingPlan.getMaterials().getMeta().getSize(), retrievedProcessingPlan.getMaterials().getMeta().getSize());
        assertEquals(originalProcessingPlan.getProducts().getMeta().getSize(), retrievedProcessingPlan.getProducts().getMeta().getSize());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ProcessingPlan originalProcessingPlan = (ProcessingPlan) originalEntity;
        ProcessingPlan updatedProcessingPlan = (ProcessingPlan) updatedEntity;

        assertNotEquals(originalProcessingPlan.getName(), updatedProcessingPlan.getName());
        assertEquals(changedField, updatedProcessingPlan.getName());
        assertEquals(originalProcessingPlan.getMaterials().getMeta().getSize(), updatedProcessingPlan.getMaterials().getMeta().getSize());
        assertEquals(originalProcessingPlan.getProducts().getMeta().getSize(), updatedProcessingPlan.getProducts().getMeta().getSize());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().processingplan();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ProcessingPlan.class;
    }
}
