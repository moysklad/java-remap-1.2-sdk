package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.GetByIdEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.GetListEndpoint;
import ru.moysklad.remap_1_2.clients.endpoints.PostEndpoint;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProcessingProcess;

public class ProcessingProcessClient extends EntityClientBase
        implements
        GetListEndpoint<ProcessingProcess>,
        GetByIdEndpoint<ProcessingProcess>,
        PostEndpoint<ProcessingProcess> {
    public ProcessingProcessClient(ApiClient api) {
        super(api, "/entity/processingprocess/");
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingProcess.class;
    }
}
