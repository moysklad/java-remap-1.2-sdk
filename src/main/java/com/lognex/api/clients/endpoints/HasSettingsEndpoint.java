package com.lognex.api.clients.endpoints;

import com.lognex.api.clients.SettingsClient;
import com.lognex.api.entities.MetaEntity;

public interface HasSettingsEndpoint<T extends MetaEntity>  extends Endpoint {
    @ApiChainElement
    default SettingsClient<T> settings() {
        return new SettingsClient<T>(api(), path(), settingsEntityClass());
    }

    Class<T> settingsEntityClass();
}
