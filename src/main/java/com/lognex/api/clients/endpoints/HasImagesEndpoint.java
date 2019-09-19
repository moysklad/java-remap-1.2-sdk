package com.lognex.api.clients.endpoints;

import com.lognex.api.entities.Image;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.responses.ListEntity;
import com.lognex.api.utils.ApiClientException;
import com.lognex.api.utils.HttpRequestExecutor;
import com.lognex.api.utils.params.ApiParam;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public interface HasImagesEndpoint <T extends MetaEntity> extends Endpoint {
    @ApiEndpoint
    default ListEntity<Image> getImages(String entityId, ApiParam... params) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + entityId + "/images")
                .apiParams(params)
                .list(Image.class);
    }

    @ApiEndpoint
    default ListEntity<Image> getImages(T entity, ApiParam... params) throws IOException, ApiClientException {
        return getImages(entity.getId(), params);
    }

    @ApiEndpoint
    default List<Image> addImage(String entityId, Image image) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + entityId + "/images")
                .body(image)
                .postList(Image.class);
    }

    @ApiEndpoint
    default List<Image> addImage(T entity, Image image) throws IOException, ApiClientException {
        return addImage(entity.getId(), image);
    }

    @ApiEndpoint
    default List<Image> updateImages(String entityId, List<Image> images) throws IOException, ApiClientException {
        return HttpRequestExecutor
                .path(api(), path() + entityId + "/images")
                .body(images)
                .postList(Image.class);
    }

    @ApiEndpoint
    default List<Image> updateImages(T entity, List<Image> images) throws IOException, ApiClientException {
        return updateImages(entity.getId(), images);
    }

    @ApiEndpoint
    default void deleteImage(String entityId, String imageId) throws IOException, ApiClientException {
        HttpRequestExecutor
                .path(api(), path() + entityId + "/images/" + imageId)
                .delete();
    }

    @ApiEndpoint
    default void deleteImage(T entity, String imageId) throws IOException, ApiClientException {
        deleteImage(entity.getId(), imageId);
    }

    @ApiEndpoint
    default void deleteImage(String entityId, Image image) throws IOException, ApiClientException {
        if (Objects.isNull(image.getId())) {
            String href = image.getMeta().getHref();
            String imageId = href.substring(href.indexOf("images/") + "images/".length());
            deleteImage(entityId, imageId);
        } else {
            deleteImage(entityId, image.getId());
        }
    }

    @ApiEndpoint
    default void deleteImage(T entity, Image image) throws IOException, ApiClientException {
        deleteImage(entity.getId(), image);
    }

    @ApiEndpoint
    default void deleteImages(String entityId, List<Image> images) throws IOException, ApiClientException {
        HttpRequestExecutor
                .path(api(), path() + entityId + "/images/delete")
                .body(images)
                .postList(Image.class);
    }

    @ApiEndpoint
    default void deleteImages(T entity, List<Image> images) throws IOException, ApiClientException {
        deleteImages(entity.getId(), images);
    }
}
