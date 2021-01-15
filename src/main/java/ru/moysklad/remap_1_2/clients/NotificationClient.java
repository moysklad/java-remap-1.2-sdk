package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.notifications.Notification;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

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
                path(api(), path() + "markasreadall").
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
