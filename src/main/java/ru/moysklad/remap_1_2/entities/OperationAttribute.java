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
public class OperationAttribute extends Attribute {

    /**
     * Флажок о том, является ли доп. поле видимым на UI. Не может быть скрытым и обязательным одновременно. Только для операций
     */
    private Boolean show;

    public OperationAttribute(String id) {
        super(id);
    }

    public OperationAttribute(Meta.Type AttributeType, String id, Type type, Object value){
        super(AttributeType, id, type, value);
    }

    public OperationAttribute(Meta.Type AttributeType, String id, MetaEntity value){
        super(AttributeType, id, value);
    }
}
