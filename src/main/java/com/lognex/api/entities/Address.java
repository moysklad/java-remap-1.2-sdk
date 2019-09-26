package com.lognex.api.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {

    private String postalCode;
    private Country country;
    private Region region;
    private String city;
    private String street;
    private String house;
    private String apartment;
    private String addInfo;
    private String comment;
}
