package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.discounts.BonusProgram;

public final class BonusProgramClient
        extends EntityClientBase
        implements
        GetListEndpoint<BonusProgram>,
        GetByIdEndpoint<BonusProgram>,
        PostEndpoint<BonusProgram>,
        PutByIdEndpoint<BonusProgram>,
        DeleteByIdEndpoint {

    public BonusProgramClient(ru.moysklad.remap_1_2.ApiClient api) {
        super(api, "/entity/bonusprogram/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return BonusProgram.class;
    }
}
