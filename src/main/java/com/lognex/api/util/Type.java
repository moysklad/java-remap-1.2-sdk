package com.lognex.api.util;

import com.lognex.api.model.base.AbstractEntity;
import com.lognex.api.model.document.PaymentIn;
import com.lognex.api.model.entity.Counterparty;
import com.lognex.api.model.entity.Employee;
import lombok.Getter;

import java.util.Arrays;

public enum Type {

    counterparty(Counterparty.class),
    paymentin(PaymentIn.class),
    employee(Employee.class);


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
