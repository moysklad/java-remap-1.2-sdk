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
    private String id;
    private String accountId;
    private Integer version;
    private String updated;
    private String name;
    private String externalCode;
    private Boolean archived;
    private List<Characteristic> characteristics;
    private Double minPrice;
    private Price buyPrice;
    private List<Price> salePrices;
    private List<String> barcodes;
    private Product product;
    private List<String> things;
}