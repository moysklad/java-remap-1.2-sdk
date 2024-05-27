package ru.moysklad.remap_1_2.entities.documents;

import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.*;
import ru.moysklad.remap_1_2.entities.documents.ProcessingPlan.ProductItem;
import ru.moysklad.remap_1_2.entities.documents.ProcessingPlan.StageItem;
import ru.moysklad.remap_1_2.entities.products.Product;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.MathUtils;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
import static org.junit.Assert.*;

public class ProcessingPlanTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, ApiClientException {
        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());

        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        Product material = simpleEntityManager.createSimple(Product.class);
        ProductItem materialItem = new ProductItem();
        materialItem.setAssortment(material);
        materialItem.setQuantity(MathUtils.round4(randomDouble(1, 5, 10)));
        processingPlan.getMaterials().getRows().add(materialItem);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        Product product = simpleEntityManager.createSimple(Product.class);
        ProductItem productItem = new ProductItem();
        productItem.setAssortment(product);
        productItem.setQuantity(MathUtils.round4(randomDouble(1, 5, 10)));
        processingPlan.getProducts().getRows().add(productItem);

        api.entity().processingplan().create(processingPlan);

        ListEntity<ProcessingPlan> updatedEntitiesList = api.entity().processingplan().
                get(limit(50), filterEq("name", processingPlan.getName()), expand("materials"), expand("products"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        ProcessingPlan retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processingPlan.getName(), retrievedEntity.getName());
        assertEquals(processingPlan.getMoment(), retrievedEntity.getMoment());
        assertEquals(materialItem.getQuantity(), retrievedEntity.getMaterials().getRows().get(0).getQuantity());
        Product retrievedMaterial = (Product) retrievedEntity.getMaterials().getRows().get(0).getAssortment();
        assertEquals(material.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        assertEquals(productItem.getQuantity(), retrievedEntity.getProducts().getRows().get(0).getQuantity());
        Product retrievedProduct = (Product) retrievedEntity.getProducts().getRows().get(0).getAssortment();
        assertEquals(product.getMeta().getHref(), retrievedProduct.getMeta().getHref());
    }

    @Test
    public void createWithStagesTest() throws IOException, ApiClientException {
        List<ProcessingStage> processingStages = Arrays.asList(simpleEntityManager.createSimple(ProcessingStage.class),
                simpleEntityManager.createSimple(ProcessingStage.class));

        ProcessingProcess process = new ProcessingProcess();
        process.setName("processingprocess_" + randomStringTail());
        process.setDescription(randomString());
        process.setPositions(new ListEntity<>());
        process.getPositions().setRows(processingStages.stream()
                .map(s -> {
                    ProcessingProcess.ProcessPosition p = new ProcessingProcess.ProcessPosition();
                    p.setProcessingstage(s);
                    return p;
                })
                .collect(Collectors.toList()));

        api.entity().processingProcess().create(process);
        process = api.entity().processingProcess().get(process.getId(), expand("positions"));

        ProcessingPlan processingPlan = new ProcessingPlan();
        processingPlan.setName("processingplan_" + randomString(3) + "_" + new Date().getTime());
        processingPlan.setProcessingProcess(process);

        processingPlan.setProducts(new ListEntity<>());
        processingPlan.getProducts().setRows(new ArrayList<>());
        Product product = simpleEntityManager.createSimple(Product.class);
        ProductItem productItem = new ProductItem();
        productItem.setAssortment(product);
        productItem.setQuantity(MathUtils.round4(randomDouble(1, 5, 10)));
        processingPlan.getProducts().getRows().add(productItem);

        Map<String, ProductItem> expectedMaterials = new HashMap<>();
        processingPlan.setMaterials(new ListEntity<>());
        processingPlan.getMaterials().setRows(new ArrayList<>());
        Product material = simpleEntityManager.createSimple(Product.class);
        ProductItem materialItem = new ProductItem();
        materialItem.setAssortment(material);
        materialItem.setQuantity(MathUtils.round4(randomDouble(1, 5, 10)));
        materialItem.setProcessingProcessPosition(process.getPositions().getRows().get(0));
        processingPlan.getMaterials().getRows().add(materialItem);
        expectedMaterials.put(process.getPositions().getRows().get(0).getId(), materialItem);

        material = simpleEntityManager.createSimple(Product.class);
        materialItem = new ProductItem();
        materialItem.setAssortment(material);
        materialItem.setQuantity(MathUtils.round4(randomDouble(1, 5, 10)));
        materialItem.setProcessingProcessPosition(process.getPositions().getRows().get(1));
        processingPlan.getMaterials().getRows().add(materialItem);
        expectedMaterials.put(process.getPositions().getRows().get(1).getId(), materialItem);

        Map<String, StageItem> expectedStages = new HashMap<>();
        processingPlan.setStages(new ListEntity<>());
        processingPlan.getStages().setRows(new ArrayList<>());
        StageItem stageItem = new StageItem();
        stageItem.setProcessingProcessPosition(process.getPositions().getRows().get(0));
        stageItem.setCost(randomLong(1000, 2000));
        stageItem.setLabourCost(randomLong(1000, 2000));
        stageItem.setStandardHour(randomLong(1000, 2000));
        processingPlan.getStages().getRows().add(stageItem);
        expectedStages.put(process.getPositions().getRows().get(0).getId(), stageItem);

        stageItem = new StageItem();
        stageItem.setProcessingProcessPosition(process.getPositions().getRows().get(1));
        stageItem.setCost(randomLong(1000, 2000));
        stageItem.setLabourCost(randomLong(1000, 2000));
        stageItem.setStandardHour(randomLong(1000, 2000));
        processingPlan.getStages().getRows().add(stageItem);
        expectedStages.put(process.getPositions().getRows().get(1).getId(), stageItem);

        api.entity().processingplan().create(processingPlan);

        ListEntity<ProcessingPlan> updatedEntitiesList = api.entity().processingplan().
                get(limit(50), filterEq("name", processingPlan.getName()), expand("materials", "products", "stages"));
        assertEquals(1, updatedEntitiesList.getRows().size());
        ProcessingPlan retrievedEntity = updatedEntitiesList.getRows().get(0);

        assertEquals(processingPlan.getName(), retrievedEntity.getName());
        assertEquals(processingPlan.getMoment(), retrievedEntity.getMoment());

        assertEquals(1, retrievedEntity.getProducts().getRows().size());
        ProductItem retrievedProductItem = retrievedEntity.getProducts().getRows().get(0);
        productItemAsserts(productItem, retrievedProductItem);

        assertEquals(2, retrievedEntity.getMaterials().getRows().size());
        for (int i = 0; i < retrievedEntity.getMaterials().getRows().size(); i++) {
            ProductItem retrievedMaterialItem = retrievedEntity.getMaterials().getRows().get(i);
            ProductItem expectedMaterialItem = expectedMaterials.get(getProcessPositionId(retrievedMaterialItem.getProcessingProcessPosition()));
            productItemAsserts(expectedMaterialItem, retrievedMaterialItem);
        }

        assertEquals(2, retrievedEntity.getStages().getRows().size());
        for (int i = 0; i < retrievedEntity.getStages().getRows().size(); i++) {
            StageItem retrievedStageItem = retrievedEntity.getStages().getRows().get(i);
            StageItem expectedStageItem = expectedStages.get(getProcessPositionId(retrievedStageItem.getProcessingProcessPosition()));
            stageItemAsserts(expectedStageItem, retrievedStageItem);
        }
    }

    @Ignore
    @Override
    public void massCreateDeleteTest() {
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
    public EntityClientBase entityClient() {
        return api.entity().processingplan();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingPlan.class;
    }

    private String getProcessPositionId(ProcessingProcess.ProcessPosition processPosition) {
        assertNotNull(processPosition);
        if (processPosition.getId() != null) {
            return processPosition.getId();
        }
        assertNotNull(processPosition.getMeta());
        String result = processPosition.getMeta().getHref() != null ?
                MetaHrefUtils.getIdFromHref(processPosition.getMeta().getHref()).orElse(null) : null;
        assertNotNull(result);
        return result;
    }

    private void productItemAsserts(ProductItem expectedItem, ProductItem actualItem) {
        assertNotNull(expectedItem);
        assertNotNull(actualItem);
        assertEquals(expectedItem.getQuantity(), actualItem.getQuantity());
        Product expectedMaterial = (Product) expectedItem.getAssortment();
        Product retrievedMaterial = (Product) actualItem.getAssortment();
        assertEquals(expectedMaterial.getMeta().getHref(), retrievedMaterial.getMeta().getHref());
        ProcessingProcess.ProcessPosition expectedProcessPosition = expectedItem.getProcessingProcessPosition();
        ProcessingProcess.ProcessPosition actualProcessPosition = actualItem.getProcessingProcessPosition();
        if (expectedProcessPosition != null && actualProcessPosition != null) {
            assertEquals(expectedProcessPosition.getMeta().getHref(), actualProcessPosition.getMeta().getHref());
        }
    }

    private void stageItemAsserts(StageItem expectedItem, StageItem actualItem) {
        assertNotNull(expectedItem);
        assertNotNull(actualItem);
        assertEquals(expectedItem.getCost(), actualItem.getCost());
        assertEquals(expectedItem.getLabourCost(), actualItem.getLabourCost());
        assertEquals(expectedItem.getStandardHour(), actualItem.getStandardHour());
    }
}
