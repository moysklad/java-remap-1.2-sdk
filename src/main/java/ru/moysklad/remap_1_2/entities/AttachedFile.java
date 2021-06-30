package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import ru.moysklad.remap_1_2.entities.agents.Employee;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttachedFile extends Attachment {
    /**
     * Дата создания
     */
    private LocalDateTime created;

    /**
     * Сотрудник, прикрепивший файл
     */
    private Employee createdBy;
}
