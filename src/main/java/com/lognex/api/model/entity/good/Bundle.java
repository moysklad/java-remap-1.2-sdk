package com.lognex.api.model.entity.good;

import com.lognex.api.model.entity.Barcode;
import com.lognex.api.model.entity.BundleComponent;
import com.lognex.api.model.entity.Country;
import com.lognex.api.model.entity.Currency;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class Bundle extends Good {

    private ProductImage image;
    private Country country;
    private String article;
    private double weight;
    private double volume;
    private List<Barcode> barcodes;
    private Overhead overhead;
    private Currency currency;
    private double value;
    private List<BundleComponent> components;
}
