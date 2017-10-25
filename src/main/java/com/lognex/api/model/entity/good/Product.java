package com.lognex.api.model.entity.good;

import com.lognex.api.model.entity.Agent;
import com.lognex.api.model.entity.Barcode;
import com.lognex.api.model.entity.Country;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Product extends Good {

    private ProductImage image;
    private double buyPrice;
    private Agent supplier;
    private Country country;
    private String article;
    private boolean weighed;
    private double weight;
    private double volume;
    private List<ProductPack> packs;
    private List<Barcode> barcodes;
    private Alcoholic alcoholic;
    private int modificationsCount;
    private double minimumBalance;
    private boolean isSerialTrackable;
    private List<Thing> things;
}
