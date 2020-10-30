package com.lognex.api.entities.documents;

import com.lognex.api.clients.EntityClientBase;
import com.lognex.api.clients.documents.DocumentMetadataClient;
import com.lognex.api.entities.EntityGetUpdateDeleteTest;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Store;
import com.lognex.api.entities.agents.Organization;
import com.lognex.api.entities.documents.positions.ProcessingOrderPosition;
import com.lognex.api.entities.products.Product;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.responses.metadata.MetadataAttributeSharedStatesResponse;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.TestUtils;
import org.junit.Test;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Optional;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProcessingOrderTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        ProcessingOrder processingOrder = new ProcessingOrder();
        processingOrder.setName("processingorder_" + randomString(3) + "_" + new Date().getTime());
        processingOrder.setDescription(randomString());
        processingOrder.setMoment(LocalDateTime.now());
        processingOrder.setOrganization(simpleEntityManager.getOwnOrganization());
        processingOrder.setStore(simpleEntityManager.getMainStore());

        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        Product material = simpleEntityManager.createSimple(Product.class);
        ProcessingPlan.PlanItem materialItem = new ProcessingPlan.PlanItem();
        materialItem.setProduct(material);
        materialItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        Product product = simpleEntityManager.createSimple(Product.class);
        ProcessingPlan.PlanItem productItem = new ProcessingPlan.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(randomDouble(1, 5, 10));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);
        processingOrder.setProcessingPlan(processingPlan);

        processingOrder.setPositions(new ListEntity<>());
        processingOrder.getPositions().setRows(new ArrayList<>());
        ProcessingOrderPosition position = new ProcessingOrderPosition();
        position.setQuantity(3.1234);
        position.setAssortment(material);
        processingOrder.getPositions().getRows().add(position);

        api.entity().processingorder().create(processingOrder);

        ListEntity<ProcessingOrder> updatedEntitiesList = api.entity().processingorder().
                get(limit(50), filterEq("name", processingOrder.getName()), expand("positions"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingOrder retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processingOrder.getName(), retrievedEntity.getName());
        assertEquals(processingOrder.getDescription(), retrievedEntity.getDescription());
        assertEquals(processingOrder.getMoment(), retrievedEntity.getMoment());
        assertEquals(processingOrder.getOrganization().getMeta().getHref(), retrievedEntity.getOrganization().getMeta().getHref());
        assertEquals(processingOrder.getStore().getMeta().getHref(), retrievedEntity.getStore().getMeta().getHref());
        assertEquals(processingOrder.getProcessingPlan().getMeta().getHref(), retrievedEntity.getProcessingPlan().getMeta().getHref());
        assertEquals(position.getQuantity(), retrievedEntity.getPositions().getRows().get(0).getQuantity());
        Product retrievedProduct = (Product) retrievedEntity.getPositions().getRows().get(0).getAssortment();
        assertEquals(material.getMeta().getHref(), retrievedProduct.getMeta().getHref());
    }

    @Test
    public void metadataTest() throws IOException, ApiClientException {
        DocumentMetadataClient<MetadataAttributeSharedStatesResponse> metadata = api.entity().processingorder().metadata();
        MetadataAttributeSharedStatesResponse response = metadata.get();

        assertFalse(response.getCreateShared());
        assertNotNull(metadata.attributes());
    }

    @Test
    public void newByProcessingPlanTest() throws IOException, ApiClientException {
        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        Product material = simpleEntityManager.createSimple(Product.class);
        ProcessingPlan.PlanItem materialItem = new ProcessingPlan.PlanItem();
        materialItem.setProduct(material);
        DecimalFormat df = TestUtils.getDoubleFormatWithFractionDigits(4);
        materialItem.setQuantity(Double.valueOf(df.format(randomDouble(1, 5, 4))));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        Product product = simpleEntityManager.createSimple(Product.class);
        ProcessingPlan.PlanItem productItem = new ProcessingPlan.PlanItem();
        productItem.setProduct(product);
        productItem.setQuantity(Double.valueOf(df.format(randomDouble(1, 5, 4))));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);

        ProcessingOrder processingOrder = api.entity().processingorder().templateDocument("processingPlan", processingPlan);

        assertEquals("", processingOrder.getName());
        assertEquals((Double) 1.0, processingOrder.getQuantity());
        assertEquals(processingPlan.getMeta().getHref(), processingOrder.getProcessingPlan().getMeta().getHref());
        assertEquals(processingPlan.getMaterials().getMeta().getSize(), (Integer) processingOrder.getPositions().getRows().size());
        assertEquals(material.getMeta().getHref(), ((Product) processingOrder.getPositions().getRows().get(0).getAssortment()).getMeta().getHref());
        assertEquals(materialItem.getQuantity(), processingOrder.getPositions().getRows().get(0).getQuantity());

        ListEntity<Organization> orgList = api.entity().organization().get();
        Optional<Organization> orgOptional = orgList.getRows().stream().
                min(Comparator.comparing(Organization::getCreated));

        Organization org = null;
        if (orgOptional.isPresent()) {
            org = orgOptional.get();
        } else {
            // Должно быть первое созданное юрлицо
            fail();
        }

        assertEquals(processingOrder.getOrganization().getMeta().getHref(), org.getMeta().getHref());

        ListEntity<Store> store = api.entity().store().get(filterEq("name", "Основной склад"));
        assertEquals(1, store.getRows().size());
        assertEquals(processingOrder.getStore().getMeta().getHref(), store.getRows().get(0).getMeta().getHref());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ProcessingOrder originalProcessingOrder = (ProcessingOrder) originalEntity;
        ProcessingOrder retrievedProcessingOrder = (ProcessingOrder) retrievedEntity;

        assertEquals(originalProcessingOrder.getName(), retrievedProcessingOrder.getName());
        assertEquals(originalProcessingOrder.getOrganization().getMeta().getHref(), retrievedProcessingOrder.getOrganization().getMeta().getHref());
        assertEquals(originalProcessingOrder.getProcessingPlan().getMeta().getHref(), retrievedProcessingOrder.getProcessingPlan().getMeta().getHref());
        assertEquals(originalProcessingOrder.getPositions().getMeta().getSize(), retrievedProcessingOrder.getPositions().getMeta().getSize());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        ProcessingOrder originalProcessingOrder = (ProcessingOrder) originalEntity;
        ProcessingOrder updatedProcessingOrder = (ProcessingOrder) updatedEntity;

        assertNotEquals(originalProcessingOrder.getName(), updatedProcessingOrder.getName());
        assertEquals(changedField, updatedProcessingOrder.getName());
        assertEquals(originalProcessingOrder.getOrganization().getMeta().getHref(), updatedProcessingOrder.getOrganization().getMeta().getHref());
        assertEquals(originalProcessingOrder.getProcessingPlan().getMeta().getHref(), updatedProcessingOrder.getProcessingPlan().getMeta().getHref());
        assertEquals(originalProcessingOrder.getPositions().getMeta().getSize(), updatedProcessingOrder.getPositions().getMeta().getSize());
    }

    @Override
    protected EntityClientBase entityClient() {
        return api.entity().processingorder();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return ProcessingOrder.class;
    }
}
