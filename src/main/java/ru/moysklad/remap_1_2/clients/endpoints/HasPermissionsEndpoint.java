package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.permissions.EmployeePermission;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.MetaHrefUtils;

import java.io.IOException;

import static ru.moysklad.remap_1_2.utils.Constants.API_PATH;

public interface HasPermissionsEndpoint extends Endpoint {
    @ApiEndpoint
    default EmployeePermission getPermissions(String id) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + id + "/security").
                get(EmployeePermission.class);
    }

    @ApiEndpoint
    default EmployeePermission updatePermissions(String id, EmployeePermission employeePermission) throws IOException, ApiClientException {
        MetaHrefUtils.fillMeta(employeePermission.getRole(), api().getHost() + API_PATH);
        final EmployeePermission result = HttpRequestExecutor
                .path(api(), path() + id + "/security")
                .body(employeePermission)
                .put(EmployeePermission.class);
        employeePermission.set(result);
        return result;
    }
}
