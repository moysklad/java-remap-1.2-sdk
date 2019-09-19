package com.lognex.api.entities.products.markers;

import com.lognex.api.entities.Image;
import com.lognex.api.responses.ListEntity;

public interface HasImages {
    ListEntity<Image> getImages();
    void setImages(ListEntity<Image> images);
}
