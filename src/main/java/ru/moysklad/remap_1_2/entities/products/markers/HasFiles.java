package ru.moysklad.remap_1_2.entities.products.markers;

import ru.moysklad.remap_1_2.entities.AttachedFile;
import ru.moysklad.remap_1_2.responses.ListEntity;

public interface HasFiles {
    ListEntity<AttachedFile> getFiles();
    void setFiles(ListEntity<AttachedFile> files);
}
