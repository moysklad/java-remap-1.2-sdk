package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.documents.DocumentEntity;
import com.lognex.api.entities.documents.DocumentPosition;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.MetaHrefUtils;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.lognex.api.utils.Constants.API_PATH;

public interface DocumentPositionsEndpoint<T extends DocumentPosition> extends Endpoint {
    @ApiEndpoint
    default List<T> createPositions(String documentId, List<T> updatedEntities) throws IOException, ApiClientException {
        updatedEntities = updatedEntities.stream()
                .map(e -> MetaHrefUtils.fillMeta(e, api().getHost() + API_PATH))
                .collect(Collectors.toList());
        List<T> responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/").
                body(updatedEntities).
                postList((Class<T>) documentPositionClass());

        for (int i = 0; i < responseEntity.size(); i++) {
            updatedEntities.set(i, responseEntity.get(i));
        }
        return updatedEntities;
    }

    @ApiEndpoint
    default List<T> createPositions(DocumentEntity document, List<T> updatedEntities) throws IOException, ApiClientException {
        return createPositions(document.getId(), updatedEntities);
    }

    @ApiEndpoint
    default T createPosition(String documentId, T updatedEntity) throws IOException, ApiClientException {
        List<T> positionList = new ArrayList<>(Collections.singletonList(updatedEntity));
        List<T> newPosition = createPositions(documentId, positionList);

        updatedEntity.set(newPosition.get(0));
        return updatedEntity;
    }

    @ApiEndpoint
    default T createPosition(DocumentEntity document, T updatedEntity) throws IOException, ApiClientException {
        return createPosition(document.getId(), updatedEntity);
    }

    @ApiEndpoint
    default ListEntity<T> getPositions(String documentId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions").
                apiParams(params).
                list((Class<T>) documentPositionClass());
    }

    @ApiEndpoint
    default ListEntity<T> getPositions(DocumentEntity document, ApiParam... params) throws IOException, ApiClientException {
        return getPositions(document.getId(), params);
    }

    @ApiEndpoint
    default T getPosition(String documentId, String positionId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                apiParams(params).
                get((Class<T>) documentPositionClass());
    }

    @ApiEndpoint
    default T getPosition(DocumentEntity document, String positionId, ApiParam... params) throws IOException, ApiClientException {
        return getPosition(document.getId(), positionId, params);
    }

    @ApiEndpoint
    default void updatePosition(String documentId, String positionId, T updatedEntity) throws IOException, ApiClientException {
        MetaHrefUtils.fillMeta(updatedEntity, api().getHost() + API_PATH);
        DocumentPosition responseEntity = HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                body(updatedEntity).
                put(DocumentPosition.class);

        updatedEntity.set(responseEntity);
    }

    @ApiEndpoint
    default void updatePosition(DocumentEntity document, String positionId, T updatedEntity) throws IOException, ApiClientException {
        updatePosition(document.getId(), positionId, updatedEntity);
    }

    @ApiEndpoint
    default void updatePosition(DocumentEntity document, T position, T updatedEntity) throws IOException, ApiClientException {
        updatePosition(document, position.getId(), updatedEntity);
    }

    @ApiEndpoint
    default void updatePosition(DocumentEntity document, T position) throws IOException, ApiClientException {
        updatePosition(document, position, position);
    }

    @ApiEndpoint
    default void deletePosition(String documentId, String positionId) throws IOException, ApiClientException {
        HttpRequestExecutor.
                path(api(), path() + documentId + "/positions/" + positionId).
                delete();
    }

    @ApiEndpoint
    default void deletePosition(DocumentEntity document, String positionId) throws IOException, ApiClientException {
        deletePosition(document.getId(), positionId);
    }

    @ApiEndpoint
    default void deletePosition(DocumentEntity document, T position) throws IOException, ApiClientException {
        deletePosition(document, position.getId());
    }

    Class<? extends DocumentPosition> documentPositionClass();
}
