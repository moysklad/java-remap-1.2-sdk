package com.lognex.api.model.entity;

import com.lognex.api.model.base.AbstractEntityInfoable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RetailStore extends AbstractEntityInfoable {
    private boolean archived;
    private String address;
    private boolean controlShippingStock;
    private boolean active;
    private boolean controlCashierChoice;
    private boolean discountEnable;
    private double discountMaxPercent;
    private PriceType priceType;
    private List<Cashier> cashiers;
    private boolean egaisEnabled;
    private String frNumber;
    private Organization organization;
    private Store store;
    private Agent acquire;
    private boolean issueOrders;
    private boolean sellReserves;
    private List lastOperationNames;//todo Последние операции Только для чтения
    private boolean ofdEnabled;
    private boolean allowCustomPrice;
    private boolean authTokenAttached;
    private State orderToState;
    private List<State> customerOrderStates;
}
