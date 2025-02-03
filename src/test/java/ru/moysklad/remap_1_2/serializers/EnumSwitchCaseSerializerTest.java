package ru.moysklad.remap_1_2.serializers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.notifications.NotificationExchange;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import static org.junit.Assert.assertEquals;

public class EnumSwitchCaseSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void testToFromJson() throws JsonProcessingException {
        ObjectMapper objectMapperCustom = ApiClient.createObjectMapper();

        NotificationExchange e = new NotificationExchange();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.NOTIFICATION_EXPORT_COMPLETED);
        e.setTaskState(NotificationExchange.TaskState.INTERRUPTED);
        e.setTaskType(NotificationExchange.TaskType.EXPORT_1C_CLIENT_BANK);

        String data = objectMapperCustom.writeValueAsString(e);
        NotificationExchange parsed = objectMapperCustom.readValue(data, NotificationExchange.class);
        assertEquals(NotificationExchange.class, parsed.getClass());
    }
}
