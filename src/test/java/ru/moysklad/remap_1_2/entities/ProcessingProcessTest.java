package ru.moysklad.remap_1_2.entities;

import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;

public class ProcessingProcessTest extends EntityGetTest {

    @Test
    public void createTest() throws IOException, ApiClientException {
        ProcessingStage stage1 = simpleEntityManager.createSimpleProcessingStage();
        ProcessingStage stage2 = simpleEntityManager.createSimpleProcessingStage();
        ProcessingStage stage3 = simpleEntityManager.createSimpleProcessingStage();
        ProcessingProcess processingProcess = new ProcessingProcess();
        processingProcess.setName("processingprocess_" + randomStringTail());
        processingProcess.setDescription(randomString());
        processingProcess.setPositions(new ListEntity<>());
        processingProcess.getPositions().setRows(new ArrayList<>());

        ProcessingProcess.ProcessPosition processPosition = new ProcessingProcess.ProcessPosition();
        processPosition.setProcessingstage(stage1);
        processPosition.setOrderingposition(0);
        processPosition.setNextPositions(Stream.of(stage2, stage3).map(ProcessingProcess.ProcessNextPosition::new).collect(Collectors.toList()));
        processingProcess.getPositions().getRows().add(processPosition);

        processPosition = new ProcessingProcess.ProcessPosition();
        processPosition.setProcessingstage(stage2);
        processPosition.setOrderingposition(1);
        processPosition.setNextPositions(Collections.singletonList(new ProcessingProcess.ProcessNextPosition(stage3)));
        processingProcess.getPositions().getRows().add(processPosition);

        processPosition = new ProcessingProcess.ProcessPosition();
        processPosition.setProcessingstage(stage3);
        processPosition.setOrderingposition(2);
        processingProcess.getPositions().getRows().add(processPosition);

        api.entity().processingProcess().create(processingProcess);
        ListEntity<ProcessingProcess> updatedEntitiesList = api.entity().processingProcess()
                .get(limit(50), filterEq("name", processingProcess.getName()), expand("positions"));

        assertEquals(1, updatedEntitiesList.getRows().size());
        ProcessingProcess retrievedEntity = updatedEntitiesList.getRows().get(0);

        assertEquals(processingProcess.getName(), retrievedEntity.getName());
        assertEquals(processingProcess.getDescription(), retrievedEntity.getDescription());
        assertEquals(processingProcess.getArchived(), retrievedEntity.getArchived());
        assertEquals(processingProcess.getPositions().getMeta().getSize(), retrievedEntity.getPositions().getMeta().getSize());

        List<ProcessingProcess.ProcessPosition> retrievedPositions = retrievedEntity.getPositions().getRows();

        assertEquals(stage1.getMeta().getHref(), retrievedPositions.get(0).getProcessingstage().getMeta().getHref());
        assertEquals(2, retrievedPositions.get(0).getNextPositions().size());
        assertEquals(stage2.getMeta().getHref(), retrievedPositions.get(0).getNextPositions().get(0).getProcessingstage().getMeta().getHref());
        assertEquals(stage3.getMeta().getHref(), retrievedPositions.get(0).getNextPositions().get(1).getProcessingstage().getMeta().getHref());

        assertEquals(stage2.getMeta().getHref(), retrievedPositions.get(1).getProcessingstage().getMeta().getHref());
        assertEquals(1, retrievedPositions.get(1).getNextPositions().size());
        assertEquals(stage3.getMeta().getHref(), retrievedPositions.get(1).getNextPositions().get(0).getProcessingstage().getMeta().getHref());

        assertEquals(stage3.getMeta().getHref(), retrievedPositions.get(2).getProcessingstage().getMeta().getHref());
        assertNull(retrievedPositions.get(2).getNextPositions());
    }


    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        ProcessingProcess originalProcess = (ProcessingProcess) originalEntity;
        ProcessingProcess retrievedProcess = (ProcessingProcess) retrievedEntity;
        assertEquals(originalProcess.getName(), retrievedProcess.getName());
        assertEquals(originalProcess.getDescription(), retrievedProcess.getDescription());
        assertEquals(originalProcess.getPositions().getMeta().getSize(), retrievedProcess.getPositions().getMeta().getSize());
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().processingProcess();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingProcess.class;
    }
}
