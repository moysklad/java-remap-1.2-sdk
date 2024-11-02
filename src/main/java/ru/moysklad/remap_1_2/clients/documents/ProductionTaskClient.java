package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.MetadataDocumentAttributeEndpoint;

public class ProductionTaskClient extends EntityClientBase implements MetadataDocumentAttributeEndpoint {
    public ProductionTaskClient(ApiClient api) {
        super(api, "/entity/productiontask/");
    }
}