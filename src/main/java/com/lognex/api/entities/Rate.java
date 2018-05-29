package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Валюта
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Rate {
    public MetaEntity currency;
}
