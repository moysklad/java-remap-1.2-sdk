package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.ApiEndpoint;
import ru.moysklad.remap_1_2.entities.notifications.NotificationSubscription;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public class NotificationSubscriptionClient
        extends EntityClientBase {

    public NotificationSubscriptionClient(ApiClient api, String path) {
        super(api, path + "subscription");
    }

    @ApiEndpoint
    public NotificationSubscription get() throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path()).
                get(NotificationSubscription.class);
    }

    @ApiEndpoint
    public void put(NotificationSubscription notificationSubscription) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path()).
                body(notificationSubscription).
                put();
    }
}
