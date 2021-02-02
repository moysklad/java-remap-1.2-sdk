package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.State;

public class StateClient
        extends EntityClientBase
        implements
        GetByIdEndpoint<State>,
        PostEndpoint<State>,
        PutByIdEndpoint<State>,
        MassCreateUpdateEndpoint<State>,
        DeleteByIdEndpoint
{

    public StateClient(ApiClient api, String path) {
        super(api, path + "metadata/states/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return State.class;
    }
}
