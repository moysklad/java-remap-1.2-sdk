package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;

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
