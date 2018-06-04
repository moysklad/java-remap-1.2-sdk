package com.lognex.api.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Товар
 */

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Product extends MetaEntity {
    private String id;
    private String accountId;
    private MetaEntity owner;
    private Boolean shared;
    private MetaEntity group;
    // syncId
    private Integer version;
    private String updated;
    private String name;
    private String description;
    private String code;
    private String externalCode;
    private Boolean archived;
    private String pathName;
    // vat
    private MetaEntity uom;
    // image
    private Double minPrice;
    private Price buyPrice;
    private List<Price> salePrices;
    // supplier
    // attributes
    // country
    private String article;
    // weighted
    // tobacco
    private Double weight;
    private Double volume;
    // packs
    private List<String> barcodes;
    // alcoholic
    private Integer modificationsCount;
    // minimumBalance
    private Boolean isSerialTrackable;
    private List<String> things;
}