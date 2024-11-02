package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Дополнительное поле
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentAttribute extends Attribute {

    /**
     * Флажок о том, является ли доп. поле видимым на UI. Не может быть скрытым и обязательным одновременно. Только для операций
     */
    private Boolean show;

    public DocumentAttribute(String id) {
        super(id);
    }

    public DocumentAttribute(Meta.Type attributeType, String id, Type type, Object value){
        super(attributeType, id, type, value);
    }

    public DocumentAttribute(Meta.Type attributeType, String id, MetaEntity value){
        super(attributeType, id, value);
    }
}
