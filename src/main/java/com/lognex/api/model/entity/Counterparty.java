package com.lognex.api.model.entity;

import com.lognex.api.converter.field.CompanyType;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class Counterparty extends Agent implements IEntityWithAttributes{

    private Set<Attribute> attributes = new HashSet<>();

    private String legalTitle;
    private String ogrn;
    private String ogrnip;
    private String okpo;
    private String certificateNumber;
    private Date certificateDate;
    private CompanyType companyType;

    public Counterparty(ID id) {
        super(id);
    }

}
