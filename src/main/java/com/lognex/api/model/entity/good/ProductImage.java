package com.lognex.api.model.entity.good;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ProductImage {

    private String title;
    private String filename;
    private int size;
    private String miniature; //todo? Ссылка на миниатюру изображения в формате Метаданных
    private String tiny; //todo? Ссылка на уменьшенное изображение в формате Метаданных
    private Date updated;
}
