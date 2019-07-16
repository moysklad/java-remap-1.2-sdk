package com.lognex.api.entities;

import com.lognex.api.clients.EntityApiClient;
import com.lognex.api.entities.agents.Counterparty;
import com.lognex.api.entities.agents.Employee;
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

public class TaskTest extends EntityGetUpdateDeleteTest {
    @Test
    public void createTest() throws IOException, LognexApiException {
        Employee adminEmpl = api.entity().employee().get(filterEq("name", "Администратор")).getRows().get(0);
        Counterparty buyerAgent = api.entity().counterparty().get(filterEq("name", "ООО \"Покупатель\"")).getRows().get(0);
        LocalDateTime dueDate = LocalDateTime.now().plusMonths(1).withSecond(0).withNano(0);

        Task task = new Task();
        task.setDescription("task_" + randomString(3) + "_" + new Date().getTime());
        task.setAssignee(adminEmpl);
        task.setAgent(buyerAgent);
        task.setDueToDate(dueDate);
        api.entity().task().create(task);

        ListEntity<Task> updatedEntitiesList = api.entity().task().get(filterEq("description", task.getDescription()));
        assertEquals(1, updatedEntitiesList.getRows().size());

        Task retrievedEntity = updatedEntitiesList.getRows().get(0);
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
            Task task = new Task();
            api.entity().task().create(task);
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
            Task task = new Task();
            task.setDescription(randomString());
            api.entity().task().create(task);
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

    @Override
    protected EntityApiClient entityClient() {
        return api.entity().task();
    }

    @Override
    protected Class<? extends MetaEntity> entityClass() {
        return Task.class;
    }
}
