package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProcessingStage;

public class ProcessingStageClient extends EntityClientBase
        implements
        GetListEndpoint<ProcessingStage>,
        PostEndpoint<ProcessingStage> {

    public ProcessingStageClient(ApiClient api) {
        super(api, "/entity/processingstage/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingStage.class;
    }
}
