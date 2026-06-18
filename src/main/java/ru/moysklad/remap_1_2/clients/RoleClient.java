package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.ApiEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.permissions.EmployeeRole;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;

import java.io.IOException;

public class RoleClient extends EntityClientBase
        implements GetListEndpoint<EmployeeRole> {
    public RoleClient(ApiClient api) {
        super(api, "/entity/role/");
    }

    @ApiEndpoint
    public EmployeeRole adminRole() throws ApiClientException, IOException {
        return HttpRequestExecutor.
                path(api(), path() + "/admin").
                get(EmployeeRole.class);
    }

    @ApiEndpoint
    public EmployeeRole cashierRole() throws ApiClientException, IOException {
        return HttpRequestExecutor.
                path(api(), path() + "/cashier").
                get(EmployeeRole.class);
    }

    @ApiEndpoint
    public EmployeeRole individualRole() throws ApiClientException, IOException {
        return HttpRequestExecutor.
                path(api(), path() + "/individual").
                get(EmployeeRole.class);
    }

    @ApiEndpoint
    public EmployeeRole customRole(String id) throws ApiClientException, IOException {
        return HttpRequestExecutor.
                path(api(), path() + "/" + id).
                get(EmployeeRole.class);
    }

    @ApiEndpoint
    public EmployeeRole updateCustomRole(String id, EmployeeRole updatedEntity) throws ApiClientException, IOException {
        return HttpRequestExecutor
                .path(api(), path() + "/" + id)
                .body(updatedEntity)
                .put(EmployeeRole.class);
    }

    @ApiEndpoint
    public EmployeeRole createCustomRole(EmployeeRole createdEntity) throws ApiClientException, IOException {
        return HttpRequestExecutor
                .path(api(), path())
                .body(createdEntity)
                .post(EmployeeRole.class);
    }

    @ApiEndpoint
    public void deleteCustomRole(String id) throws ApiClientException, IOException {
        HttpRequestExecutor
                .path(api(), path() + "/" + id)
                .delete();
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return EmployeeRole.class;
    }
}
