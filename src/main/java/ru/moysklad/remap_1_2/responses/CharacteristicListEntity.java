package ru.moysklad.remap_1_2.responses;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.moysklad.remap_1_2.entities.Context;
import ru.moysklad.remap_1_2.entities.Fetchable;
import ru.moysklad.remap_1_2.entities.MetaEntity;
import ru.moysklad.remap_1_2.entities.products.Variant;

import java.util.List;

/**
 * Список характеристик
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class CharacteristicListEntity extends MetaEntity implements Fetchable {
    private Context context;
    private List<Variant.Characteristic> characteristics;
}
