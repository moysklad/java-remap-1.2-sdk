package com.lognex.api.model.entity;

import com.lognex.api.model.base.EntityWithOwner;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Barcode extends EntityWithOwner {
    private Consignment consignment;
    private String barcode;
}
