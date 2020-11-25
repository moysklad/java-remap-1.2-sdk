package com.lognex.api.clients;

import com.lognex.api.ApiClient;
import com.lognex.api.clients.endpoints.*;
import com.lognex.api.entities.MetaEntity;

public class SettingsClient<T extends MetaEntity> extends EntityClientBase
        implements
        GetEndpoint<T>,
        PutEndpoint<T>
{
    private final Class<T> settingsClass;

    public SettingsClient(ApiClient api, String path, Class<T> settingsClass) {
        super(api, path + "settings/");
        this.settingsClass = settingsClass;
    }

    @Override
    public Class<T> entityClass() {
        return settingsClass;
    }
}
