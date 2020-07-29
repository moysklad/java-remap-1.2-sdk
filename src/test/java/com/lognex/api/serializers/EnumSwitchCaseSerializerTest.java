package com.lognex.api.serializers;

import com.google.gson.Gson;
import com.lognex.api.ApiClient;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.notifications.NotificationExchange;
import com.lognex.api.utils.TestAsserts;
import com.lognex.api.utils.TestRandomizers;
import org.junit.Test;

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
