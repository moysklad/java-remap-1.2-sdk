package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.entities.AttachedFile;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.responses.ListEntity;
import ru.moysklad.remap_1_2.utils.ApiClientException;
import ru.moysklad.remap_1_2.utils.HttpRequestExecutor;
import ru.moysklad.remap_1_2.utils.params.ApiParam;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public interface HasFilesEndpoint<T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default ListEntity<AttachedFile> getFiles(String entityId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + entityId + "/files")
                .apiParams(params)
                .list(AttachedFile.class);
    }

    @ApiEndpoint
    default ListEntity<AttachedFile> getFiles(T entity, ApiParam... params) throws IOException, ApiClientException {
        return getFiles(entity.getId(), params);
    }

    @ApiEndpoint
    default List<AttachedFile> addFile(String entityId, AttachedFile file) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + entityId + "/files")
                .body(file)
                .postList(AttachedFile.class);
    }

    @ApiEndpoint
    default List<AttachedFile> addFile(T entity, AttachedFile file) throws IOException, ApiClientException {
        return addFile(entity.getId(), file);
    }

    @ApiEndpoint
    default List<AttachedFile> updateFiles(String entityId, List<AttachedFile> files) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + entityId + "/files")
                .body(files)
                .postList(AttachedFile.class);
    }

    @ApiEndpoint
    default List<AttachedFile> updateFiles(T entity, List<AttachedFile> files) throws IOException, ApiClientException {
        return updateFiles(entity.getId(), files);
    }

    @ApiEndpoint
    default void deleteFile(String entityId, String fileId) throws IOException, ApiClientException {
        HttpRequestExecutor
                .path(api(), path() + entityId + "/files/" + fileId)
                .delete();
    }

    @ApiEndpoint
    default void deleteFile(T entity, String fileId) throws IOException, ApiClientException {
        deleteFile(entity.getId(), fileId);
    }

    @ApiEndpoint
    default void deleteFile(String entityId, AttachedFile file) throws IOException, ApiClientException {
        if (Objects.isNull(file.getId())) {
            String href = file.getMeta().getHref();
            String fileId = href.substring(href.indexOf("files/") + "files/".length());
            deleteFile(entityId, fileId);
        } else {
            deleteFile(entityId, file.getId());
        }
    }

    @ApiEndpoint
    default void deleteFile(T entity, AttachedFile file) throws IOException, ApiClientException {
        deleteFile(entity.getId(), file);
    }

    @ApiEndpoint
    default void deleteFiles(String entityId, List<AttachedFile> files) throws IOException, ApiClientException {
        HttpRequestExecutor
                .path(api(), path() + entityId + "/files/delete")
                .body(files)
                .postList(AttachedFile.class);
    }

    @ApiEndpoint
    default void deleteFiles(T entity, List<AttachedFile> files) throws IOException, ApiClientException {
        deleteFiles(entity.getId(), files);
    }
}
