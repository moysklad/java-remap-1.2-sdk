package com.moysklad.api.entity;

import com.moysklad.api.util.ID;

import java.util.Date;

public abstract class AbstractOperation extends AbstractEntityLegendable {
    private Date moment;
    private boolean applicable;
    private Double sum;
    private ID contract;
    private ID project;
    private ID state;
}
