package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.permissions.EmployeePermission;
import ru.moysklad.remap_1_2.entities.permissions.EmployeePermissions;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public interface HasPermissionsEndpoint extends Endpoint {
    @ApiEndpoint
    default EmployeePermission getPermissions(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + id + "/security").
                get(EmployeePermission.class);
    }

    @ApiEndpoint
    default EmployeePermission updatePermissions(String id, EmployeePermissions employeePermissions) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + id + "/security")
                .body(employeePermissions)
                .put(EmployeePermission.class);
    }
}
