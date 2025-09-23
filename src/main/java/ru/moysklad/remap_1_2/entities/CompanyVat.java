package ru.moysklad.remap_1_2.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CompanyVat {
    Boolean useCompanyVat;
    BigDecimal defaultCompanyVat;
    Boolean defaultCompanyVatEnabled;
}
