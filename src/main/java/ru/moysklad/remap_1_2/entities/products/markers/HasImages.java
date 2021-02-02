package ru.moysklad.remap_1_2.entities.products.markers;

import ru.moysklad.remap_1_2.entities.Image;
import ru.moysklad.remap_1_2.responses.ListEntity;

public interface HasImages {
    ListEntity<Image> getImages();
    void setImages(ListEntity<Image> images);
}
