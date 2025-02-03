package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;

public class ProcessingStageTest extends EntityGetTest {

    @Test
    public void createTest() throws IOException, ApiClientException {
        ProcessingStage processingStage = new ProcessingStage();
        processingStage.setName("processingstage_" + randomString(3) + "_" + new Date().getTime());
        processingStage.setArchived(false);
        processingStage.setDescription(randomString());
        processingStage.setExternalCode(randomString());
        processingStage.setStandardHourCost(3.33);

        api.entity().processingStage().create(processingStage);
        ListEntity<ProcessingStage> updatedEntitiesList = api.entity().processingStage().get(filterEq("name", processingStage.getName()));

        assertEquals(1, updatedEntitiesList.getRows().size());
        ProcessingStage retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(processingStage.getName(), retrievedEntity.getName());
        assertEquals(processingStage.getDescription(), retrievedEntity.getDescription());
        assertEquals(processingStage.getArchived(), retrievedEntity.getArchived());
        assertEquals(processingStage.getStandardHourCost(), retrievedEntity.getStandardHourCost());
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ProcessingStage originalStage = (ProcessingStage) originalEntity;
        ProcessingStage retrievedStage = (ProcessingStage) retrievedEntity;
        assertEquals(originalStage.getName(), retrievedStage.getName());
        assertEquals(originalStage.getDescription(), retrievedStage.getDescription());
        assertEquals(originalStage.getStandardHourCost(), retrievedStage.getStandardHourCost());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().processingStage();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingStage.class;
    }
}
