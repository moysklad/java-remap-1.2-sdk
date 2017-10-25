package com.lognex.api.model.document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FactureIn extends Facture {

    private List<Supply> supplies = new ArrayList<>();
    private String incomingNumber;
    private Date incomingDate;
}