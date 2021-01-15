package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.DeleteByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PostEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PutByIdEndpoint;
import ru.moysklad.remap_1_2.entities.Group;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public final class GroupClient
        extends EntityClientBase
        implements
        GetListEndpoint<Group>,
        GetByIdEndpoint<Group>,
        PostEndpoint<Group>,
        PutByIdEndpoint<Group>,
        DeleteByIdEndpoint {

    public GroupClient(ApiClient api) {
        super(api, "/entity/group/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Group.class;
    }
}
