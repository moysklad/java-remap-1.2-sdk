package com.lognex.api.responses;

import com.lognex.api.entities.MetaEntity;
import com.lognex.api.entities.PriceEntity;
import com.lognex.api.entities.products.VariantEntity;
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
    private List<VariantEntity.Characteristic> characteristics;
    private List<PriceEntity> priceTypes;
    private Boolean createShared;
}
