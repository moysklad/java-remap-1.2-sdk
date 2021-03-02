package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.permissions.EmployeePermission;
import ru.moysklad.remap_1_2.entities.permissions.MailActivationRequired;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface HasAccessManagmentEndpoint extends Endpoint {
    @ApiEndpoint
    default void resetPassword(String id) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + id + "/access/resetpassword").
                put();
    }

    @ApiEndpoint
    default void deactivate(String id) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + id + "/access/deactivate").
                put();
    }

    @ApiEndpoint
    default MailActivationRequired activate(String id, EmployeePermission employeePermission) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + id + "/access/activate").
                body(employeePermission).
                put(MailActivationRequired.class);
    }
}
