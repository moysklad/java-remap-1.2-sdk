package com.lognex.api.util;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.document.Demand;
import com.lognex.api.model.document.FactureOut;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.*;
import lombok.Getter;

import java.util.Arrays;

public enum Type {

    COUNTERPARTY(Counterparty.class),
    PAYMENTIN(PaymentIn.class),
    EMPLOYEE(Employee.class),
    CUSTOMENTITY(CustomEntity.class),
    PRODUCT(Product.class),
    SERVICE(Service.class),
    CONSIGNMENT(Consignment.class),
    BUNDLE(Bundle.class),
    VARIANT(Variant.class),
    STORE(Store.class),
    CURRENCY(Currency.class),
    DEMAND(Demand.class),
    ORGANIZATION(Organization.class),
    FACTUREOUT(FactureOut.class),
    GROUP(Group.class)
    ;


    @Getter
    final Class<? extends AbstractEntity> modelClass;

    Type(Class<? extends AbstractEntity> clazz){
        this.modelClass = clazz;
    }

    public static Type find(Class<? extends AbstractEntity> clazz){
        return Arrays.stream(values())
                        .filter(t -> t.modelClass.getSimpleName().equals(clazz.getSimpleName()))
                        .findFirst().orElseThrow(() -> new IllegalStateException("No type found for class: " + clazz.getSimpleName()));
    }
}
