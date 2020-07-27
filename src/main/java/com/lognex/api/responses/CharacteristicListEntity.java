package com.lognex.api.responses;

import com.lognex.api.entities.Context;
import com.lognex.api.entities.Fetchable;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.products.Variant;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
