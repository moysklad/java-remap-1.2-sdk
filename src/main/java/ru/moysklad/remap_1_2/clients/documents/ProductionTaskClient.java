package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.MetadataAttributeOperationEndpoint;

public class ProductionTaskClient extends EntityClientBase implements MetadataAttributeOperationEndpoint {
    public ProductionTaskClient(ApiClient api) {
        super(api, "/entity/productiontask/");
    }
}