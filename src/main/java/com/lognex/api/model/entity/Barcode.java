package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityWithOwner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Barcode extends AbstractEntityWithOwner {
    private Consignment consignment;
    private String barcode;
}
