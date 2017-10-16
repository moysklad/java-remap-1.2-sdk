package com.lognex.api.model.entity;

import com.lognex.api.model.entity.item.Sex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CurrencyUnit {
    private Sex gender;
    private String s1;
    private String s2;
    private String s5;
}
