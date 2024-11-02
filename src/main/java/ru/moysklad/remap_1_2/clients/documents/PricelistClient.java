package ru.moysklad.remap_1_2.clients.documents;

import ru.moysklad.remap_1_2.ApiClient;
import ru.moysklad.remap_1_2.clients.EntityClientBase;
import ru.moysklad.remap_1_2.clients.endpoints.*;
import ru.moysklad.remap_1_2.entities.DocumentAttribute;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.documents.Pricelist;
import ru.moysklad.remap_1_2.entities.documents.Pricelist.PricelistRow;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.responses.metadata.MetadataAttributeSharedStatesResponse;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PricelistClient
        extends EntityClientBase
        implements
        GetListEndpoint<Pricelist>,
        PostEndpoint<Pricelist>,
        DeleteByIdEndpoint,
        DocumentMetadataEndpoint<MetadataAttributeSharedStatesResponse<DocumentAttribute>>,
        MetadataDocumentAttributeEndpoint,
        GetByIdEndpoint<Pricelist>,
        PutByIdEndpoint<Pricelist>,
        MassCreateUpdateDeleteEndpoint<Pricelist>,
        ExportEndpoint,
        PublicationEndpoint,
        HasStatesEndpoint,
        HasFilesEndpoint<Pricelist> {

    public PricelistClient(ApiClient api) {
        super(api, "/entity/pricelist/");
    }

    @ApiEndpoint
    public List<PricelistRow> postPositions(String documentId, List<PricelistRow> updatedEntities) throws IOException, ApiClientException {
        List<PricelistRow> responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/").
                body(updatedEntities).
                postList(PricelistRow.class);

        for (int i = 0; i < responseEntity.size(); i++) {
            updatedEntities.set(i, responseEntity.get(i));
        }
        return updatedEntities;
    }

    @ApiEndpoint
    public List<PricelistRow> postPositions(Pricelist document, List<PricelistRow> updatedEntities) throws IOException, ApiClientException {
        return postPositions(document.getId(), updatedEntities);
    }

    @ApiEndpoint
    public PricelistRow postPosition(String documentId, PricelistRow updatedEntity) throws IOException, ApiClientException {
        List<PricelistRow> positionList = new ArrayList<>(Collections.singletonList(updatedEntity));
        List<PricelistRow> newPosition = postPositions(documentId, positionList);

        updatedEntity.set(newPosition.get(0));
        return updatedEntity;
    }

    @ApiEndpoint
    public PricelistRow postPosition(Pricelist document, PricelistRow updatedEntity) throws IOException, ApiClientException {
        return postPosition(document.getId(), updatedEntity);
    }

    @ApiEndpoint
    public ListEntity<PricelistRow> getPositions(String documentId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions").
                apiParams(params).
                list(PricelistRow.class);
    }

    @ApiEndpoint
    public ListEntity<PricelistRow> getPositions(Pricelist document, ApiParam... params) throws IOException, ApiClientException {
        return getPositions(document.getId(), params);
    }

    @ApiEndpoint
    public PricelistRow getPosition(String documentId, String positionId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                apiParams(params).
                get(PricelistRow.class);
    }

    @ApiEndpoint
    public PricelistRow getPosition(Pricelist document, String positionId, ApiParam... params) throws IOException, ApiClientException {
        return getPosition(document.getId(), positionId, params);
    }

    @ApiEndpoint
    public void putPosition(String documentId, String positionId, PricelistRow updatedEntity) throws IOException, ApiClientException {
        PricelistRow responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                body(updatedEntity).
                put(PricelistRow.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    public void putPosition(Pricelist document, String positionId, PricelistRow updatedEntity) throws IOException, ApiClientException {
        putPosition(document.getId(), positionId, updatedEntity);
    }

    @ApiEndpoint
    public void putPosition(Pricelist document, PricelistRow position, PricelistRow updatedEntity) throws IOException, ApiClientException {
        putPosition(document, position.getId(), updatedEntity);
    }

    @ApiEndpoint
    public void putPosition(Pricelist document, PricelistRow position) throws IOException, ApiClientException {
        putPosition(document, position, position);
    }

    @ApiEndpoint
    public void delete(String documentId, String positionId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                delete();
    }

    @ApiEndpoint
    public void delete(Pricelist document, String positionId) throws IOException, ApiClientException {
        delete(document.getId(), positionId);
    }

    @ApiEndpoint
    public void delete(Pricelist document, PricelistRow position) throws IOException, ApiClientException {
        delete(document, position.getId());
    }

    @Override
    public Class<? extends MetaEntity> entityClass() {
        return Pricelist.class;
    }

    @Override
    public Class<? extends MetaEntity> metaEntityClass() {
        return MetadataAttributeSharedStatesResponse.class;
    }
}
