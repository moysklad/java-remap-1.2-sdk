package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Модификация
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Variant extends MetaEntity {
    public String id;
    public String accountId;
    public Integer version;
    public String updated;
    public String name;
    public String externalCode;
    public Boolean archived;
    public List<Characteristic> characteristics;
    public Double minPrice;
    public Price buyPrice;
    public List<Price> salePrices;
    public List<String> barcodes;
    public Product product;
    public List<String> things;
}