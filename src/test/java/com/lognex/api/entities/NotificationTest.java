package com.lognex.api.entities;

import com.lognex.api.entities.notifications.NotificationSubscription;
import com.lognex.api.utils.ApiClientException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static com.lognex.api.entities.notifications.NotificationSubscription.Channel.EMAIL;
import static com.lognex.api.entities.notifications.NotificationSubscription.Channel.PUSH;
import static org.junit.Assert.*;

public class NotificationTest extends EntityTestBase {
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
}
