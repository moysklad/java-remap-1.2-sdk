package com.lognex.api.entities;

import com.lognex.api.entities.agents.EmployeeEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ProductFolderEntity extends MetaEntity {
    /**
     * Ссылка на Владельца (Сотрудника) в формате Метаданных)
     */
    private EmployeeEntity owner;

    /**
     * Наименование Группы товаров, в которую входит данная Группа товаров
     */
    private String pathName;

    /**
     * Общий доступ
     */
    private Boolean shared;

    /**
     * Добавлена ли Группа товаров в архив
     */
    private Boolean archived;

    /**
     * Код Группы товаров
     */
    private String code;

    /**
     * Внешний код Группы товаров
     */
    private String externalCode;

    /**
     * Описание Группы товаров
     */
    private String description;

    /**
     * Ссылка на Группу товаров данной Группы товаров в формате Метаданных
     */
    private ProductFolderEntity productFolder;

    /**
     * Момент последнего обновления сущности
     */
    private LocalDateTime updated;

    /**
     * Отдел сотрудника в формате Метаданных
     */
    private GroupEntity group;
}
