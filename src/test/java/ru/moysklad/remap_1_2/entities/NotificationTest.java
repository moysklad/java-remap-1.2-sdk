package ru.moysklad.remap_1_2.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.notifications.*;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.MockHttpClient;
import ru.moysklad.remap_1_2.utils.TestConstants;
import ru.moysklad.remap_1_2.utils.TestUtils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static ru.moysklad.remap_1_2.entities.Meta.Type.*;
import static ru.moysklad.remap_1_2.entities.notifications.NotificationExchange.TaskType.EXPORT_GOOD;
import static ru.moysklad.remap_1_2.entities.notifications.NotificationExchange.TaskType.IMPORTER_CSV_AGENT;
import static ru.moysklad.remap_1_2.entities.notifications.NotificationSubscription.Channel.EMAIL;
import static ru.moysklad.remap_1_2.entities.notifications.NotificationSubscription.Channel.PUSH;
import static org.junit.Assert.*;

public class NotificationTest extends EntityTestBase {
    private ApiClient mockApi;
    private MockHttpClient mockHttpClient;
    private String host;

    @Test
    public void testSettings() throws IOException, ApiClientException {
        NotificationSubscription notificationSubscription = new NotificationSubscription();
        notificationSubscription.setGroups(new NotificationSubscription.NotificationGroups());
        notificationSubscription.getGroups().setCustomerOrder(new NotificationSubscription.NotificationGroup(true, Collections.singletonList(PUSH)));
        notificationSubscription.getGroups().setDataExchange(new NotificationSubscription.NotificationGroup(false, Collections.singletonList(PUSH)));
        notificationSubscription.getGroups().setInvoice(new NotificationSubscription.NotificationGroup(false, new ArrayList<>()));
        notificationSubscription.getGroups().setRetail(new NotificationSubscription.NotificationGroup(false, Collections.singletonList(EMAIL)));
        notificationSubscription.getGroups().setStock(new NotificationSubscription.NotificationGroup(true, Collections.singletonList(EMAIL)));
        notificationSubscription.getGroups().setTask(new NotificationSubscription.NotificationGroup(false, Arrays.asList(EMAIL, PUSH)));
        api.notification().subscription().put(notificationSubscription);

        notificationSubscription = api.notification().subscription().get();
        assertTrue(notificationSubscription.getGroups().getCustomerOrder().getEnabled());
        assertFalse(notificationSubscription.getGroups().getDataExchange().getEnabled());
        assertFalse(notificationSubscription.getGroups().getInvoice().getEnabled());
        assertFalse(notificationSubscription.getGroups().getRetail().getEnabled());
        assertTrue(notificationSubscription.getGroups().getStock().getEnabled());
        assertFalse(notificationSubscription.getGroups().getTask().getEnabled());

        assertEquals(PUSH, notificationSubscription.getGroups().getCustomerOrder().getChannels().get(0));
        assertEquals(PUSH, notificationSubscription.getGroups().getDataExchange().getChannels().get(0));
        assertEquals(0, notificationSubscription.getGroups().getInvoice().getChannels().size());
        assertEquals(EMAIL, notificationSubscription.getGroups().getRetail().getChannels().get(0));
        assertEquals(EMAIL, notificationSubscription.getGroups().getStock().getChannels().get(0));
        assertEquals(Arrays.asList(EMAIL, PUSH), notificationSubscription.getGroups().getTask().getChannels());
    }

    @Before
    public void enableAllNotifications() throws IOException, ApiClientException {
        NotificationSubscription notificationSubscription = new NotificationSubscription();
        notificationSubscription.setGroups(new NotificationSubscription.NotificationGroups());
        notificationSubscription.getGroups().setCustomerOrder(new NotificationSubscription.NotificationGroup(true, Arrays.asList(EMAIL, PUSH)));
        notificationSubscription.getGroups().setDataExchange(new NotificationSubscription.NotificationGroup(true, Arrays.asList(EMAIL, PUSH)));
        notificationSubscription.getGroups().setInvoice(new NotificationSubscription.NotificationGroup(true, Arrays.asList(EMAIL, PUSH)));
        notificationSubscription.getGroups().setRetail(new NotificationSubscription.NotificationGroup(true, Arrays.asList(EMAIL, PUSH)));
        notificationSubscription.getGroups().setStock(new NotificationSubscription.NotificationGroup(true, Arrays.asList(EMAIL, PUSH)));
        notificationSubscription.getGroups().setTask(new NotificationSubscription.NotificationGroup(true, Arrays.asList(EMAIL, PUSH)));
        api.notification().subscription().put(notificationSubscription);
    }

    @Test
    public void testNotificationOrderNew() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/ordernew.json"), Notification.class
        );

        assertEquals(NotificationCustomerOrder.class, notification.getClass());

        assertEquals(NOTIFICATION_ORDER_NEW, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Новый заказ покупателя № 00001", notification.getTitle());
        assertEquals("Сумма: 499.99. Покупатель: Розничный покупатель.", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/customerorder/c5ab5d93-56e1-11e9-c0a8-100a0000001d",
                ((NotificationCustomerOrder) notification).getOrder().getMeta().getHref());
        assertEquals("00001", ((NotificationCustomerOrder) notification).getOrder().getName());
        assertEquals(Long.valueOf(49999), ((NotificationCustomerOrder) notification).getOrder().getSum());
        assertEquals("Розничный покупатель", ((NotificationCustomerOrder) notification).getOrder().getAgentName());
    }

    @Test
    public void testNotificationOrderOverdue() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue(
                TestUtils.getFile("notificationJson/orderoverdue.json"), Notification.class
        );

        assertEquals(NotificationCustomerOrder.class, notification.getClass());

        assertEquals(NOTIFICATION_ORDER_OVERDUE, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Просрочен заказ покупателя № 00001", notification.getTitle());
        assertEquals("Просрочен заказ покупателя № 00001 Сумма: 300.00. Покупатель: Розничный покупатель.", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/customerorder/c5ab5d93-56e1-11e9-c0a8-100a0000001d",
                ((NotificationCustomerOrder) notification).getOrder().getMeta().getHref());
        assertEquals("00001", ((NotificationCustomerOrder) notification).getOrder().getName());
        assertEquals(Long.valueOf(30000), ((NotificationCustomerOrder) notification).getOrder().getSum());
        assertEquals("Розничный покупатель", ((NotificationCustomerOrder) notification).getOrder().getAgentName());
    }

    @Test
    public void testNotificationInvoiceOutOverdue() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/invoiceoutoverdue.json"), Notification.class
        );

        assertEquals(NotificationInvoiceOutOverdue.class, notification.getClass());

        assertEquals(NOTIFICATION_INVOICE_OUT_OVERDUE, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Счет покупателю №00003 просрочен", notification.getTitle());
        assertEquals("Дата оплаты: 01.04.2019 20:08. Сумма: 500,00. Покупатель: Розничный покупатель.", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/invoiceout/571b1ac4-56fc-11e9-c0a8-100e0000002b",
                ((NotificationInvoiceOutOverdue) notification).getInvoice().getMeta().getHref());
        assertEquals("00003", ((NotificationInvoiceOutOverdue) notification).getInvoice().getName());
        assertEquals(Long.valueOf(50000), ((NotificationInvoiceOutOverdue) notification).getInvoice().getSum());
        assertEquals("Розничный покупатель", ((NotificationInvoiceOutOverdue) notification).getInvoice().getCustomerName());
    }

    @Test
    public void testNotificationGoodCountTooLow() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/goodcounttoolow.json"), Notification.class
        );

        assertEquals(NotificationGoodCountTooLow.class, notification.getClass());

        assertEquals(NOTIFICATION_GOOD_COUNT_TOO_LOW, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Заканчивается товар 1", notification.getTitle());
        assertEquals("Остаток ниже 200", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/product/bdca925e-56e1-11e9-c0a8-100a00000016",
                ((NotificationGoodCountTooLow) notification).getGood().getMeta().getHref());
        assertEquals("1", ((NotificationGoodCountTooLow) notification).getGood().getName());
        assertEquals(Double.valueOf(180.0), ((NotificationGoodCountTooLow) notification).getActualBalance());
        assertEquals(Double.valueOf(200.0), ((NotificationGoodCountTooLow) notification).getMinimumBalance());
    }

    @Test
    public void testNotificationTaskAssigned() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskassigned.json"), Notification.class
        );

        assertEquals(NotificationTask.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_ASSIGNED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Новая задача: Текст задачи 1", notification.getTitle());
        assertEquals("ntest1 назначил вам задачу Текст задачи 1", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTask) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTask) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTask) notification).getTask().getMeta().getHref());
        assertEquals("Текст задачи 1", ((NotificationTask) notification).getTask().getName());
    }

    @Test
    public void testNotificationTaskUnassigned() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskunassigned.json"), Notification.class
        );

        assertEquals(NotificationTask.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_UNASSIGNED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Задача снята: Новый текст задачи 1", notification.getTitle());
        assertEquals("ntest1 снял с вас задачу", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTask) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTask) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTask) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTask) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 27, 15, 49, 0, 0),
                ((NotificationTask) notification).getTask().getDeadline());
    }

    @Test
    public void testNotificationTaskOverdue() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskoverdue.json"), Notification.class
        );

        assertEquals(NotificationTask.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_OVERDUE, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Просрочена задача: Новый текст задачи 1", notification.getTitle());
        assertEquals("Задача просрочена Новый текст задачи 1 Срок: 27.05.2019 15:49", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTask) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTask) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 27, 15, 49, 0, 0),
                ((NotificationTask) notification).getTask().getDeadline());
    }

    @Test
    public void testNotificationTaskCompleted() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskcompleted.json"), Notification.class
        );

        assertEquals(NotificationTask.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_COMPLETED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Задача выполнена: Новый текст задачи 1", notification.getTitle());
        assertEquals("ntest1 выполнил задачу Новый текст задачи 1 Срок: 29.05.2019 15:47", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTask) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTask) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTask) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTask) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 29, 15, 47, 0, 0),
                ((NotificationTask) notification).getTask().getDeadline());
    }

    @Test
    public void testNotificationTaskReopened() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskreopened.json"), Notification.class
        );

        assertEquals(NotificationTask.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_REOPENED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Задача открыта: Новый текст задачи 1", notification.getTitle());
        assertEquals("ntest1 открыл задачу Новый текст задачи 1 Срок: 29.05.2019 15:47", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTask) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTask) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTask) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTask) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 29, 15, 47, 0, 0),
                ((NotificationTask) notification).getTask().getDeadline());
    }

    @Test
    public void testNotificationTaskDeleted() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskdeleted.json"), Notification.class
        );

        assertEquals(NotificationTask.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_DELETED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Задача удалена: Текст задачи 2", notification.getTitle());
        assertEquals("ntest1 удалил задачу Текст задачи 2", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTask) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTask) notification).getPerformedBy().getName());
        assertEquals("Текст задачи 2", ((NotificationTask) notification).getTask().getName());
    }

    @Test
    public void testNotificationTaskNewComment() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/tasknewcomment.json"), Notification.class
        );

        assertEquals(NotificationTaskComment.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_NEW_COMMENT, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Новый комментарий: Новый текст задачи 1", notification.getTitle());
        assertEquals("ntest1 добавил комментарий Комментарий 1", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTaskComment) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTaskComment) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTaskComment) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTaskComment) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 27, 15, 49, 0, 0),
                ((NotificationTaskComment) notification).getTask().getDeadline());
        assertEquals("Комментарий 1", ((NotificationTaskComment) notification).getNoteContent());
    }

    @Test
    public void testNotificationTaskCommentChanged() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskcommentchanged.json"), Notification.class
        );

        assertEquals(NotificationTaskChanged.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_COMMENT_CHANGED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Комментарий изменен: Новый текст задачи 1", notification.getTitle());
        assertEquals("ntest1 изменил комментарий Новый текст комментария 1, Комментарий 1", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTaskChanged) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTaskChanged) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTaskChanged) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTaskChanged) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 27, 15, 49, 0, 0),
                ((NotificationTaskChanged) notification).getTask().getDeadline());
        NotificationTaskChanged.Diff diff = new NotificationTaskChanged.Diff();
        diff.setNoteContent(new NotificationTaskChanged.Change<>());
        diff.getNoteContent().setOldValue("Комментарий 1");
        diff.getNoteContent().setNewValue("Новый текст комментария 1");
        assertEquals(diff.getNoteContent().getOldValue(), ((NotificationTaskChanged) notification).getDiff().getNoteContent().getOldValue());
        assertEquals(diff.getNoteContent().getNewValue(), ((NotificationTaskChanged) notification).getDiff().getNoteContent().getNewValue());
    }

    @Test
    public void testNotificationTaskCommentDeleted() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskcommentdeleted.json"), Notification.class
        );

        assertEquals(NotificationTaskComment.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_COMMENT_DELETED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Комментарий удален: Новый текст задачи 1", notification.getTitle());
        assertEquals("ntest1 удалил комментарий Новый текст комментария 1", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTaskComment) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTaskComment) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTaskComment) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTaskComment) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 27, 15, 49, 0, 0),
                ((NotificationTaskComment) notification).getTask().getDeadline());
        assertEquals("Новый текст комментария 1", ((NotificationTaskComment) notification).getNoteContent());
    }

    @Test
    public void testNotificationTaskChanged() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/taskchanged.json"), Notification.class
        );

        assertEquals(NotificationTaskChanged.class, notification.getClass());

        assertEquals(NOTIFICATION_TASK_CHANGED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Задача изменена: Новый текст задачи 1", notification.getTitle());
        assertEquals("ntest1 изменил задачу", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/c4e1397b-807c-11e9-9ff4-31500025d4ed",
                ((NotificationTaskChanged) notification).getPerformedBy().getMeta().getHref());
        assertEquals("ntest1", ((NotificationTaskChanged) notification).getPerformedBy().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/task/91d6e8a5-807d-11e9-9109-f8fc0024968d",
                ((NotificationTaskChanged) notification).getTask().getMeta().getHref());
        assertEquals("Новый текст задачи 1", ((NotificationTaskChanged) notification).getTask().getName());
        assertEquals(LocalDateTime.of(2019, Month.MAY, 27, 15, 49, 0, 0),
                ((NotificationTaskChanged) notification).getTask().getDeadline());
        NotificationTaskChanged.Diff diff = new NotificationTaskChanged.Diff();
        diff.setDeadline(new NotificationTaskChanged.Change<>());
        diff.getDeadline().setOldValue(LocalDateTime.of(2019, Month.MAY, 29, 15, 47, 0, 0));
        diff.getDeadline().setNewValue(LocalDateTime.of(2019, Month.MAY, 27, 15, 49, 0, 0));
        assertEquals(diff.getDeadline().getOldValue(), ((NotificationTaskChanged) notification).getDiff().getDeadline().getOldValue());
        assertEquals(diff.getDeadline().getNewValue(), ((NotificationTaskChanged) notification).getDiff().getDeadline().getNewValue());
    }

    @Test
    public void testNotificationImportCompleted() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/importcompleted.json"), Notification.class
        );

        assertEquals(NotificationExchange.class, notification.getClass());

        assertEquals(NOTIFICATION_IMPORT_COMPLETED, notification.getMeta().getType());
        assertTrue(notification.getRead());
        assertEquals("Импорт завершен", notification.getTitle());
        assertEquals("Импорт контрагентов (Excel). Обработано 3 строки, создано 0 контрагентов, обновлено 0 контрагентов.", notification.getDescription());
        assertEquals(IMPORTER_CSV_AGENT, ((NotificationExchange) notification).getTaskType());
        assertEquals(NotificationExchange.TaskState.COMPLETED, ((NotificationExchange) notification).getTaskState());
    }

    @Test
    public void testNotificationExportCompleted() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/exportcompleted.json"), Notification.class
        );

        assertEquals(NotificationExchange.class, notification.getClass());

        assertEquals(NOTIFICATION_EXPORT_COMPLETED, notification.getMeta().getType());
        assertTrue(notification.getRead());
        assertEquals("Экспорт завершен", notification.getTitle());
        assertEquals("Экспорт товаров (Excel). Экспортировано 43465 товаров и 2080 модификаций", notification.getDescription());
        assertEquals("Экспортировано 43465 товаров и 2080 модификаций", ((NotificationExchange) notification).getMessage());
        assertEquals(EXPORT_GOOD, ((NotificationExchange) notification).getTaskType());
        assertEquals(NotificationExchange.TaskState.COMPLETED, ((NotificationExchange) notification).getTaskState());
    }

    @Test
    public void testNotificationSubscribeExpired() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/subscribeexpired.json"), Notification.class
        );

        assertEquals(NotificationSubscribeExpired.class, notification.getClass());

        assertEquals(NOTIFICATION_SUBSCRIBE_EXPIRED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("Окончание подписки", notification.getTitle());
        assertEquals("Подписка на аккаунте AccountName окончена. Вы по-прежнему можете работать, используя бесплатный тарифный план. Подписка возобновится при пополнении баланса.", notification.getDescription());
    }

    @Test
    public void testNotificationSubscribeTermsExpired() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/subscribetermsexpired.json"), Notification.class
        );

        assertEquals(NotificationSubscribeTermsExpired.class, notification.getClass());

        assertEquals(NOTIFICATION_SUBSCRIBE_TERMS_EXPIRED, notification.getMeta().getType());
        assertFalse(notification.getRead());
        assertEquals("До окончания подписки 5 дней", notification.getTitle());
        assertEquals("До окончания подписки на аккаунте AccountName осталось 3 дней. Рекомендуем заранее пополнить баланс.", notification.getDescription());
        assertEquals(Integer.valueOf(5), ((NotificationSubscribeTermsExpired) notification).getDaysLeft());
    }

    @Test
    public void testNotificationRetailShiftOpened() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/retailshiftopened.json"), Notification.class
        );

        assertEquals(NotificationRetailShift.class, notification.getClass());

        assertEquals(NOTIFICATION_RETAIL_SHIFT_OPENED, notification.getMeta().getType());
        assertTrue(notification.getRead());
        assertEquals("Открыта смена в Точка продаж", notification.getTitle());
        assertEquals("Кассир: Кассир Кладкин", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/4d4ba195-0e7b-11e2-480d-3c4a92f3a0a7",
                ((NotificationRetailShift) notification).getUser().getMeta().getHref());
        assertEquals("Кассир Кладкин", ((NotificationRetailShift) notification).getUser().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/retailstore/ffa5bbf4-351b-11e9-9ff4-34e800131be8",
                ((NotificationRetailShift) notification).getRetailStore().getMeta().getHref());
        assertEquals("Точка продаж", ((NotificationRetailShift) notification).getRetailStore().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/retailshift/7e41bd3c-351c-11e9-9ff4-34e80012cfc1",
                ((NotificationRetailShift) notification).getRetailShift().getMeta().getHref());
        assertEquals("00002", ((NotificationRetailShift) notification).getRetailShift().getName());
        assertEquals(LocalDateTime.of(2019, Month.FEBRUARY, 20, 17, 33, 0, 0),
                ((NotificationRetailShift) notification).getRetailShift().getOpen());
        assertEquals(Long.valueOf(3000000), ((NotificationRetailShift) notification).getRetailShift().getProceed());
    }

    @Test
    public void testNotificationRetailShiftClosed() throws JsonProcessingException {
        ObjectMapper objectMapper = ApiClient.createObjectMapper();

        Notification notification = objectMapper.readValue (
                TestUtils.getFile("notificationJson/retailshiftclosed.json"), Notification.class
        );

        assertEquals(NotificationRetailShiftClosed.class, notification.getClass());

        assertEquals(NOTIFICATION_RETAIL_SHIFT_CLOSED, notification.getMeta().getType());
        assertTrue(notification.getRead());
        assertEquals("Закрыта смена в Точка продаж", notification.getTitle());
        assertEquals("Кассир: Кассир Кладкин. Длительность: 1 мин. Продаж: 2. Возвратов: 0. Выручка: 40000.00", notification.getDescription());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/employee/4d4ba195-0e7b-11e2-480d-3c4a92f3a0a7",
                ((NotificationRetailShiftClosed) notification).getUser().getMeta().getHref());
        assertEquals("Кассир Кладкин", ((NotificationRetailShiftClosed) notification).getUser().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/retailstore/ffa5bbf4-351b-11e9-9ff4-34e800131be8",
                ((NotificationRetailShiftClosed) notification).getRetailStore().getMeta().getHref());
        assertEquals("Точка продаж", ((NotificationRetailShiftClosed) notification).getRetailStore().getName());
        assertEquals("https://api.moysklad.ru/api/remap/1.2/entity/retailshift/08fd47a8-351c-11e9-9109-f8fc0013f6cd",
                ((NotificationRetailShiftClosed) notification).getRetailShift().getMeta().getHref());
        assertEquals("00001", ((NotificationRetailShiftClosed) notification).getRetailShift().getName());
        assertEquals(LocalDateTime.of(2019, Month.FEBRUARY, 20, 17, 30, 0, 0),
                ((NotificationRetailShiftClosed) notification).getRetailShift().getOpen());
        assertEquals(LocalDateTime.of(2019, Month.FEBRUARY, 20, 17, 31, 11, 0),
                ((NotificationRetailShiftClosed) notification).getRetailShift().getClose());
        assertEquals(Long.valueOf(4000000), ((NotificationRetailShiftClosed) notification).getRetailShift().getProceed());
        assertEquals(Integer.valueOf(2), ((NotificationRetailShiftClosed) notification).getSales());
        assertEquals(Integer.valueOf(0), ((NotificationRetailShiftClosed) notification).getReturns());
    }

    @Test
    public void testMarkAsRead() throws IOException, ApiClientException {
        host = api.getHost();

        mockHttpClient = new MockHttpClient();
        mockApi = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD"),
                mockHttpClient
        );

        mockApi.notification().markAsRead("ID");

        assertEquals(
                host + "/api/remap/1.2/notification/ID/markasread",
                mockHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }

    @Test
    public void testMarkAsReadAll() throws IOException, ApiClientException {
        host = api.getHost();

        mockHttpClient = new MockHttpClient();
        mockApi = new ApiClient(
                System.getenv("API_HOST"),
                TestConstants.FORCE_HTTPS_FOR_TESTS, System.getenv("API_LOGIN"),
                System.getenv("API_PASSWORD"),
                mockHttpClient
        );

        mockApi.notification().markAsReadAll();

        assertEquals(
                host + "/api/remap/1.2/notification/markasreadall",
                mockHttpClient.getLastExecutedRequest().getRequestLine().getUri()
        );
    }
}
