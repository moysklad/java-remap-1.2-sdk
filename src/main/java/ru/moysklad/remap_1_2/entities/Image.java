package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Image extends MetaEntity {
    /**
     * Название изображения
     */
    private String title;

    /**
     * Имя файла
     */
    private String filename;

    /**
     * Изображение, закодированное в формате Base64
     */
    private String content;

    /**
     * Размер файла в байтах
     */
    private Long size;

    /**
     * Дата последнего изменения
     */
    private LocalDateTime updated;

    /**
     * Ссылка на миниатюру изображения
     */
    private Meta miniature;

    /**
     * Ссылка на уменьшенное изображение
     */
    private Meta tiny;

    public Image(String id) {
        super(id);
    }

    /**
     * Считывает файл и заполняет поля <pre>filename</pre> и <pre>content</pre>.
     * Нужно для упрощения загрузки изображения.
     *
     * @param file Файл изображения, который нужно загрузить
     * @throws IOException когда возникла проблема с чтением файла
     */
    public void setContent(File file) throws IOException {
        byte[] encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(file));
        filename = file.getName();
        content = new String(encoded, StandardCharsets.UTF_8);
    }
}
