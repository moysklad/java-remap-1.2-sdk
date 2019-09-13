package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.notifications.Notification;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;

import java.io.IOException;

public class NotificationClient
        extends EntityClientBase
        implements
        GetListEndpoint<Notification>,
        GetByIdEndpoint<Notification>,
        DeleteByIdEndpoint {

    public NotificationClient(ApiClient api) {
        super(api, "/notification/");
    }

    @ApiEndpoint
    public void markAsRead(String id) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + id + "/markasread").
                put();
    }

    @ApiEndpoint
    public void markAsRead(Notification notification) throws IOException, ApiClientException {
        markAsRead(notification.getId());
    }

    @ApiEndpoint
    public void markAsReadAll() throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + "/markasreadall").
                put();
    }

    @ApiChainElement
    public NotificationSubscriptionClient subscription() {
        return new NotificationSubscriptionClient(api(), path());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Notification.class;
    }
}
