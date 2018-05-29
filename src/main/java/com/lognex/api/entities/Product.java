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
    public String id;
    public String accountId;
    public MetaEntity owner;
    public Boolean shared;
    public MetaEntity group;
    // syncId
    public Integer version;
    public String updated;
    public String name;
    public String description;
    public String code;
    public String externalCode;
    public Boolean archived;
    public String pathName;
    // vat
    public MetaEntity uom;
    // image
    public Double minPrice;
    public Price buyPrice;
    public List<Price> salePrices;
    // supplier
    // attributes
    // country
    public String article;
    // weighted
    // tobacco
    public Double weight;
    public Double volume;
    // packs
    public List<String> barcodes;
    // alcoholic
    public Integer modificationsCount;
    // minimumBalance
    public Boolean isSerialTrackable;
    public List<String> things;
}