package com.lognex.api.responses;

import com.lognex.api.entities.Characteristic;
import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.Price;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Список Метаданных Модификаций
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public final class VariantMetadataListResponse extends MetaEntity {
    private List<Characteristic> characteristics;
    private List<Price> priceTypes;
    private Boolean createShared;
}
