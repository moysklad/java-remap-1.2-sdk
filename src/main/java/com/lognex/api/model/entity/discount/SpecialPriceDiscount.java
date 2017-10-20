package com.lognex.api.model.entity.discount;

import com.lognex.api.model.entity.PriceType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecialPriceDiscount extends Discount {
    private String specialPrice; //Спец. цена (если выбран тип цен)

    private boolean usingPriceType;
    private PriceType priceType; //Наименование типа цены
    private String value; //Значение цены, если выбрано фиксированное значение
}
