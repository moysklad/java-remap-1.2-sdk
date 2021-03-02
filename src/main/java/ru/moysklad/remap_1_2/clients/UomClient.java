package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.Uom;

public final class UomClient
        extends EntityClientBase
        implements
        GetListEndpoint<Uom>,
        PostEndpoint<Uom>,
        DeleteByIdEndpoint,
        GetByIdEndpoint<Uom>,
        PutByIdEndpoint<Uom>,
        MassCreateUpdateDeleteEndpoint<Uom> {

    public UomClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/uom/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Uom.class;
    }
}
