package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.permissions.EmployeePermission;
import com.lognex.api.entities.permissions.MailActivationRequired;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;

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
