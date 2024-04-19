package ru.moysklad.remap_1_2.clients;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.ProcessingProcess;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;

public class ProcessingProcessClient extends EntityClientBase
        implements
        GetListEndpoint<ProcessingProcess>,
        PostEndpoint<ProcessingProcess> {
    public ProcessingProcessClient(ApiClient api) {
        super(api, "/entity/processingprocess/");
    }

    @ApiEndpoint
    public ListEntity<ProcessingProcess.ProcessingProcessPosition> getPositions(String processId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + processId + "/positions")
                .apiParams(params)
                .list(ProcessingProcess.ProcessingProcessPosition.class);
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return ProcessingProcess.class;
    }
}
