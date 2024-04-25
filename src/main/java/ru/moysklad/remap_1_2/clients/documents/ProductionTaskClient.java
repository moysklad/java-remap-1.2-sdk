package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.MetadataAttributeEndpoint;

public class ProductionTaskClient extends EntityClientBase implements MetadataAttributeEndpoint {
    public ProductionTaskClient(ApiClient api) {
        super(api, "/entity/productiontask/");
    }
}