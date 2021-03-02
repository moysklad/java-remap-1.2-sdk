package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.clients.SettingsClient;
import ru.moysklad.remap_1_2.entities.MetaEntity;

public interface HasSettingsEndpoint<T extends MetaEntity>  extends Endpoint {
    @ApiChainElement
    default SettingsClient<T> settings() {
        return new SettingsClient<T>(api(), path(), settingsEntityClass());
    }

    Class<T> settingsEntityClass();
}
