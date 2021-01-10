package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.permissions.EmployeePermission;
import com.lognex.api.entities.permissions.EmployeePermissions;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;

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
