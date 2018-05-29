package com.lognex.api.responses;

import com.lognex.api.entities.Characteristic;
import com.lognex.api.entities.Meta;
import com.lognex.api.entities.Price;

import java.util.List;

/**
 * Список Метаданных Модификаций
 */
public final class VariantMetadataListResponse {
    public Meta meta;
    public List<Characteristic> characteristics;
    public List<Price> priceTypes;
    public Boolean createShared;
}
