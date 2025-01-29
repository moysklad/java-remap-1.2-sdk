package ru.moysklad.remap_1_2.entities;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.RequestLine;
import org.junit.Ignore;
import org.junit.Test;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.entities.agents.Counterparty;
import ru.moysklad.remap_1_2.entities.agents.Employee;
import ru.moysklad.remap_1_2.entities.documents.Demand;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.params.FilterParam;
import ru.moysklad.remap_1_2.utils.params.OrderParam;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ru.moysklad.remap_1_2.utils.params.ExpandParam.expand;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filter;
import static ru.moysklad.remap_1_2.utils.params.FilterParam.filterEq;
import static ru.moysklad.remap_1_2.utils.params.LimitParam.limit;
import static ru.moysklad.remap_1_2.utils.params.OffsetParam.offset;
import static ru.moysklad.remap_1_2.utils.params.OrderParam.order;
import static org.junit.Assert.*;

public class TaskTest extends EntityGetUpdateDeleteTest implements FilesTest<Task> {
    @Test
    public void createTest() throws IOException, ApiClientException {
        Employee adminEmpl = simpleEntityManager.getAdminEmployee();
        Counterparty buyerAgent = api.entity().counterparty().get(filterEq("name", "ООО \"Покупатель\"")).getRows().get(0);
        LocalDateTime dueDate = LocalDateTime.now().plusMonths(1).withSecond(0).withNano(0);

        Task task = new Task();
        task.setDescription("task_" + randomString(3) + "_" + new Date().getTime());
        task.setAssignee(adminEmpl);
        task.setAgent(buyerAgent);
        task.setDueToDate(dueDate);
        task.setDone(false);
        api.entity().task().create(task);

        ListEntity<Task> updatedEntitiesList = api.entity().task().get(filterEq("description", task.getDescription()), expand("notes"));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Task retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(adminEmpl.getMeta().getHref(), retrievedEntity.getAuthor().getMeta().getHref());
        assertEquals(adminEmpl.getMeta().getHref(), retrievedEntity.getAssignee().getMeta().getHref());
        assertEquals(buyerAgent.getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(dueDate, retrievedEntity.getDueToDate());
        assertFalse(retrievedEntity.getDone());
        assertEquals(task.getImplementer(), retrievedEntity.getImplementer());
    }

    @Test
    public void serializeOperationField() throws Exception {
        Demand demand = simpleEntityManager.createSimpleDemand();
        Task task = simpleEntityManager.createSimpleTask();
        task.setOperation(demand);
        api.entity().task().update(task);

        Task taskFromApi = api.entity().task().get(task.getId());
        assertEquals(taskFromApi.getOperation().meta.getHref(), demand.getMeta().getHref());
    }

    @Test
    public void updateTaskNullAgent() throws Exception {
        Employee adminEmpl = simpleEntityManager.getAdminEmployee();
        Counterparty buyerAgent = api.entity().counterparty().get(filterEq("name", "ООО \"Покупатель\"")).getRows().get(0);
        LocalDateTime dueDate = LocalDateTime.now().plusMonths(1).withSecond(0).withNano(0);

        Task task = new Task();
        task.setDescription("task_" + randomString(3) + "_" + new Date().getTime());
        task.setAssignee(adminEmpl);
        task.setAgent(buyerAgent);
        task.setDueToDate(dueDate);
        task.setDone(false);
        api.entity().task().create(task);

        Task updatedTask = new Task();
        updatedTask.setId(task.getId());
        updatedTask.setDescription("task_" + randomString(3) + "_" + new Date().getTime());
        api.entity().task().update(updatedTask);

        Task taskFromApi = api.entity().task().get(task.getId());
        assertNotNull(taskFromApi.getAgent());

        updatedTask = new Task();
        updatedTask.setId(task.getId());
        updatedTask.setAgent(null);
        api.entity().task().update(updatedTask);

        taskFromApi = api.entity().task().get(task.getId());
        assertNull(taskFromApi.getAgent());
    }

    @Override
    protected String getFieldNameToUpdate() {
        return "Description";
    }

    @Override
    @Test
    public void deleteTest() throws IOException, ApiClientException {
        doDeleteTest("Description");
        doDeleteByIdTest("Description");
    }

    @Test
    public void offsetTest() throws IOException, ApiClientException {
        int value = randomInteger(1, 100);
        mockApi.entity().task().get(offset(value));

        RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
        assertEquals("GET", rl.getMethod());
        assertEquals("https://test.moysklad/api/remap/1.2/entity/task/?offset=" + value, rl.getUri());
    }

    @Test
    public void limitTest() throws IOException, ApiClientException {
        int value = randomInteger(1, 100);
        mockApi.entity().task().get(limit(value));

        RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
        assertEquals("GET", rl.getMethod());
        assertEquals("https://test.moysklad/api/remap/1.2/entity/task/?limit=" + value, rl.getUri());
    }

    @Test
    public void expandOrderFilterTest() throws IOException, ApiClientException {
        {
            String value = randomString();
            mockApi.entity().task().get(expand(value));

            RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
            assertEquals("GET", rl.getMethod());
            assertEquals("https://test.moysklad/api/remap/1.2/entity/task/?expand=" + value, rl.getUri());
        }

        {
            String value = randomString();
            OrderParam.Direction dir = randomEnumGeneric(OrderParam.Direction.class);
            mockApi.entity().task().get(order(value, dir));

            RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
            assertEquals("GET", rl.getMethod());
            assertEquals(
                    "https://test.moysklad/api/remap/1.2/entity/task/?order=" +
                            URLEncoder.encode(value + "," + dir.name(), "UTF-8"),
                    rl.getUri()
            );
        }

        {
            String key = randomString();
            FilterParam.FilterType op = randomEnumGeneric(FilterParam.FilterType.class);
            String value = randomString();
            mockApi.entity().task().get(filter(key, op, value));

            RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
            assertEquals("GET", rl.getMethod());
            assertEquals(
                    "https://test.moysklad/api/remap/1.2/entity/task/?filter=" +
                            URLEncoder.encode(key + op.getStr() + value, "UTF-8"),
                    rl.getUri()
            );
        }


        {
            String expandValue = randomString();
            String orderValue = randomString();
            OrderParam.Direction orderDir = randomEnumGeneric(OrderParam.Direction.class);
            String filterKey = randomString();
            FilterParam.FilterType filterOp = randomEnumGeneric(FilterParam.FilterType.class);
            String filterValue = randomString();

            mockApi.entity().task().get(
                    expand(expandValue),
                    order(orderValue, orderDir),
                    filter(filterKey, filterOp, filterValue)
            );

            RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
            assertEquals("GET", rl.getMethod());
            assertEquals(
                    "https://test.moysklad/api/remap/1.2/entity/task/?filter=" +
                            URLEncoder.encode(filterKey + filterOp.getStr() + filterValue, "UTF-8") + "&" +
                            "expand=" + expandValue + "&" +
                            "order=" + URLEncoder.encode(orderValue + "," + orderDir.name(), "UTF-8"),
                    rl.getUri()
            );
        }
    }

    @Test
    public void errorTest() throws IOException {
        try {
            Task task = new Task();
            api.entity().task().create(task);
            fail("Ожидалось исключение");
        } catch (ApiClientException task) {
            assertApiError(
                    task, 412,
                    Arrays.asList(
                            Pair.of(3000, "Ошибка сохранения объекта: поле 'description' не может быть пустым или отсутствовать"),
                            Pair.of(3000, "Ошибка сохранения объекта: поле 'assignee' не может быть пустым или отсутствовать")
                    )
            );
        }

        try {
            Task task = new Task();
            task.setDescription(randomString());
            api.entity().task().create(task);
            fail("Ожидалось исключение");
        } catch (ApiClientException task) {
            assertApiError(
                    task, 412,
                    3000, "Ошибка сохранения объекта: поле 'assignee' не может быть пустым или отсутствовать"
            );
        }
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        Task originalTask = (Task) originalEntity;
        Task retrievedTask = (Task) retrievedEntity;

        assertEquals(originalTask.getId(), retrievedTask.getId());
        assertEquals(originalTask.getDescription(), retrievedTask.getDescription());
        assertEquals(originalTask.getAuthor().getMeta().getHref(), retrievedTask.getAuthor().getMeta().getHref());
        assertEquals(originalTask.getAssignee().getMeta().getHref(), retrievedTask.getAssignee().getMeta().getHref());
        assertNull(retrievedTask.getAgent());
        assertNull(retrievedTask.getDueToDate());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        Task originalTask = (Task) originalEntity;
        Task updatedTask = (Task) updatedEntity;

        assertEquals(originalTask.getId(), updatedTask.getId());
        assertNotEquals(originalTask.getDescription(), updatedTask.getDescription());
        assertEquals(changedField, updatedTask.getDescription());
        assertEquals(originalTask.getAuthor().getMeta().getHref(), updatedTask.getAuthor().getMeta().getHref());
        assertEquals(originalTask.getAssignee().getMeta().getHref(), updatedTask.getAssignee().getMeta().getHref());
    }

    @Test
    public void createNoteTest() throws IOException, ApiClientException {
        Task task = simpleEntityManager.createSimpleTask();
        Task.TaskNote note = new Task.TaskNote();
        String text = randomString();
        note.setText(text);

        api.entity().task().createNote(task, note);

        Task.TaskNote retrievedNote = api.entity().task().getNote(task, note);

        assertEquals(text, retrievedNote.getText());
    }

    @Test
    public void getNotesTest() throws IOException, ApiClientException {
        Task task = simpleEntityManager.createSimpleTask();

        List<String> texts = new ArrayList<>();

        for (int i = 0; i < 2; ++i) {
            texts.add(randomString());
            Task.TaskNote note = new Task.TaskNote();
            note.setText(texts.get(i));
            api.entity().task().createNote(task, note);
        }

        ListEntity<Task.TaskNote> retrievedNotes = api.entity().task().getNotes(task);

        assertEquals(2, retrievedNotes.getRows().size());

        assertTrue(retrievedNotes.getRows().stream()
                .allMatch(p -> texts.stream().anyMatch(t -> t.equals(p.getText())))
        );
    }

    @Test
    public void updateNoteTest() throws IOException, ApiClientException {
        Task task = simpleEntityManager.createSimple(Task.class);
        Task.TaskNote note = new Task.TaskNote();
        String text = randomString();
        note.setText(text);

        api.entity().task().createNote(task, note);

        String updatedText = randomString();
        note.setText(updatedText);
        api.entity().task().updateNote(task, note);

        Task.TaskNote retrievedNote = api.entity().task().getNote(task, note);

        assertNotEquals(text, retrievedNote.getText());
        assertEquals(updatedText, retrievedNote.getText());
    }

    @Test
    public void deleteNoteTest() throws IOException, ApiClientException {
        Task task = simpleEntityManager.createSimpleTask();
        Task.TaskNote note = new Task.TaskNote();
        String text = randomString();
        note.setText(text);

        api.entity().task().createNote(task, note);
        ListEntity<Task.TaskNote> retrievedNotes = api.entity().task().getNotes(task);
        assertEquals(1, retrievedNotes.getRows().size());

        api.entity().task().deleteNote(task, note);
        retrievedNotes = api.entity().task().getNotes(task);
        assertEquals(0, retrievedNotes.getRows().size());
    }

    @Test
    public void testFiles() throws IOException, ApiClientException {
        doTestFiles();
    }

    @Ignore
    @Override
    public void massCreateDeleteTest() {
    }

    @Override
    public EntityClientBase entityClient() {
        return api.entity().task();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Task.class;
    }
}
