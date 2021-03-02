package ru.moysklad.remap_1_2.serializers;

import com.google.gson.Gson;
import org.junit.Test;
import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.entities.Meta;
import ru.moysklad.remap_1_2.entities.notifications.NotificationExchange;
import ru.moysklad.remap_1_2.utils.TestAsserts;
import ru.moysklad.remap_1_2.utils.TestRandomizers;

import static org.junit.Assert.assertEquals;

public class EnumSwitchCaseSerializerTest implements TestAsserts, TestRandomizers {
    @Test
    public void testToFromJson() {
        Gson gsonCustom = ApiClient.createGson();

        NotificationExchange e = new NotificationExchange();
        e.setMeta(new Meta());
        e.getMeta().setType(Meta.Type.NOTIFICATION_EXPORT_COMPLETED);
        e.setTaskState(NotificationExchange.TaskState.INTERRUPTED);
        e.setTaskType(NotificationExchange.TaskType.EXPORT_1C_CLIENT_BANK);

        String data = gsonCustom.toJson(e);
        NotificationExchange parsed = gsonCustom.fromJson(data, NotificationExchange.class);
        assertEquals(NotificationExchange.class, parsed.getClass());
    }
}
