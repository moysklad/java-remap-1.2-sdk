package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.notifications.NotificationSubscription;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;

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
