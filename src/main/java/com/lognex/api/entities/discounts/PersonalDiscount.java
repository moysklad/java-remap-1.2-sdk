package com.lognex.api.entities.discounts;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PersonalDiscount extends GoodDiscount {
    public PersonalDiscount(String id) {
        super(id);
    }
}
