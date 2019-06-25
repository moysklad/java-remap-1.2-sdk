package com.lognex.api.entities;

import com.lognex.api.clients.ApiClient;
import com.lognex.api.entities.agents.CounterpartyEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.LognexApiException;
import com.lognex.api.utils.params.FilterParam;
import com.lognex.api.utils.params.OrderParam;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.RequestLine;
import org.junit.Test;

import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;

import static com.lognex.api.utils.params.ExpandParam.expand;
import static com.lognex.api.utils.params.FilterParam.filter;
import static com.lognex.api.utils.params.FilterParam.filterEq;
import static com.lognex.api.utils.params.LimitParam.limit;
import static com.lognex.api.utils.params.OffsetParam.offset;
import static com.lognex.api.utils.params.OrderParam.order;
import static org.junit.Assert.*;

public class TaskEntityTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        EmployeeEntity adminEmpl = api.entity().employee().get(filterEq("name", "Администратор")).getRows().get(0);
        CounterpartyEntity buyerAgent = api.entity().counterparty().get(filterEq("name", "ООО \"Покупатель\"")).getRows().get(0);
        LocalDateTime dueDate = LocalDateTime.now().plusMonths(1).withSecond(0).withNano(0);

        TaskEntity task = new TaskEntity();
        task.setDescription("task_" + randomString(3) + "_" + new Date().getTime());
        task.setAssignee(adminEmpl);
        task.setAgent(buyerAgent);
        task.setDueToDate(dueDate);
        api.entity().task().post(task);

        ListEntity<TaskEntity> updatedEntitiesList = api.entity().task().get(filterEq("description", task.getDescription()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        TaskEntity retrievedEntity = updatedEntitiesList.getRows().get(0);
        assertEquals(adminEmpl.getMeta().getHref(), retrievedEntity.getAuthor().getMeta().getHref());
        assertEquals(adminEmpl.getMeta().getHref(), retrievedEntity.getAssignee().getMeta().getHref());
        assertEquals(buyerAgent.getMeta().getHref(), retrievedEntity.getAgent().getMeta().getHref());
        assertEquals(dueDate, retrievedEntity.getDueToDate());
        assertFalse(retrievedEntity.getDone());
    }

    @Override
    @Test
    public void putTest() throws IOException, LognexApiException {
        doPutTest("Description");
    }

    @Override
    @Test
    public void deleteTest() throws IOException, LognexApiException {
        doDeleteTest("Description");
        doDeleteByIdTest("Description");
    }

    @Test
    public void offsetTest() throws IOException, LognexApiException {
        int value = randomInteger(1, 100);
        mockApi.entity().task().get(offset(value));

        RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
        assertEquals("GET", rl.getMethod());
        assertEquals("https://test.moysklad/api/remap/1.2/entity/task/?offset=" + value, rl.getUri());
    }

    @Test
    public void limitTest() throws IOException, LognexApiException {
        int value = randomInteger(1, 100);
        mockApi.entity().task().get(limit(value));

        RequestLine rl = mockHttpClient.getLastExecutedRequest().getRequestLine();
        assertEquals("GET", rl.getMethod());
        assertEquals("https://test.moysklad/api/remap/1.2/entity/task/?limit=" + value, rl.getUri());
    }

    @Test
    public void expandOrderFilterTest() throws IOException, LognexApiException {
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
            TaskEntity task = new TaskEntity();
            api.entity().task().post(task);
            fail("Ожидалось исключение");
        } catch (LognexApiException task) {
            assertApiError(
                    task, 412,
                    Arrays.asList(
                            Pair.of(3000, "Ошибка сохранения объекта: поле 'description' не может быть пустым или отсутствовать"),
                            Pair.of(3000, "Ошибка сохранения объекта: поле 'assignee' не может быть пустым или отсутствовать")
                    )
            );
        }

        try {
            TaskEntity task = new TaskEntity();
            task.setDescription(randomString());
            api.entity().task().post(task);
            fail("Ожидалось исключение");
        } catch (LognexApiException task) {
            assertApiError(
                    task, 412,
                    3000, "Ошибка сохранения объекта: поле 'assignee' не может быть пустым или отсутствовать"
            );
        }
    }

    @Override
    protected void getAsserts(MetaEntity originalEntity, MetaEntity retrievedEntity) {
        TaskEntity originalTask = (TaskEntity) originalEntity;
        TaskEntity retrievedTask = (TaskEntity) retrievedEntity;

        assertEquals(originalTask.getId(), retrievedTask.getId());
        assertEquals(originalTask.getDescription(), retrievedTask.getDescription());
        assertEquals(originalTask.getAuthor().getMeta().getHref(), retrievedTask.getAuthor().getMeta().getHref());
        assertEquals(originalTask.getAssignee().getMeta().getHref(), retrievedTask.getAssignee().getMeta().getHref());
        assertNull(retrievedTask.getAgent());
        assertNull(retrievedTask.getDueToDate());
    }

    @Override
    protected void putAsserts(MetaEntity originalEntity, MetaEntity updatedEntity, Object changedField) {
        TaskEntity originalTask = (TaskEntity) originalEntity;
        TaskEntity updatedTask = (TaskEntity) updatedEntity;

        assertEquals(originalTask.getId(), updatedTask.getId());
        assertNotEquals(originalTask.getDescription(), updatedTask.getDescription());
        assertEquals(changedField, updatedTask.getDescription());
        assertEquals(originalTask.getAuthor().getMeta().getHref(), updatedTask.getAuthor().getMeta().getHref());
        assertEquals(originalTask.getAssignee().getMeta().getHref(), updatedTask.getAssignee().getMeta().getHref());
    }

    @Override
    protected ApiClient entityClient() {
        return api.entity().task();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return TaskEntity.class;
    }
}
