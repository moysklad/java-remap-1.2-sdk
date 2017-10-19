package com.lognex.api.model.entity;

import com.lognex.api.model.base.field.CompanyType;
import com.lognex.api.model.base.IEntityWithAttributes;
import com.lognex.api.model.entity.attribute.Attribute;
import com.lognex.api.util.ID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@NoArgsConstructor
public class Counterparty extends Agent implements IEntityWithAttributes {

    private Set<Attribute<?>> attributes = new HashSet<>();

    private String legalTitle;
    private String ogrn;
    private String ogrnip;
    private String okpo;
    private String certificateNumber;
    private Date certificateDate;
    private CompanyType companyType;
    private String email;
    private List<AgentAccount> accounts = new ArrayList<>();
    private List<String> tags = new ArrayList<>();

    public Counterparty(ID id) {
        super(id);
    }

    @Override
    public Set<Attribute<?>> getAttributes(){
        return attributes;
    }

    @Override
    public Attribute<?> getAttribute(String attributeId) {
        return attributes.stream().filter(a -> a.getId().equals(attributeId))
                .findFirst().orElse(null);
    }

}
