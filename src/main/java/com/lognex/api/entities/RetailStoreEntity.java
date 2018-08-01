package com.lognex.api.entities;

import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailStoreEntity extends MetaEntity implements Fetchable {
    private Boolean shared;
    private Boolean controlCashierChoice;
    private String externalCode;
    private Object frNumber;
    private Boolean controlShippingStock;
    private Boolean ofdEnabled;
    private Boolean archived;
    private Boolean issueOrders;
    private List<LastOperationNamesItem> lastOperationNames;
    private State state;
    private GroupEntity group;
    private EmployeeEntity owner;
    private Boolean allowCustomPrice;
    private String priceType;
    private MetaEntity cashiers;
    private Boolean active;
    private StoreEntity store;
    private Boolean discountEnable;
    private Environment environment;
    private Boolean sellReserves;
    private Boolean authTokenAttached;
    private Boolean egaisEnabled;
    private OrganizationEntity organization;
    private Integer discountMaxPercent;
    private String updated;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class LastOperationNamesItem {
        private String name;
        private String entity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Environment {
        private Software software;
        private ChequePrinter chequePrinter;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Software {

        }

        @Getter
        @Setter
        @NoArgsConstructor
        public static class ChequePrinter {
            private Driver driver;
            private FiscalMemory fiscalMemory;

            public class Driver {

            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class State {
        private Sync sync;
        private FiscalMemory fiscalMemory;

        public static class Sync {

        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FiscalMemory {
        private Error error;

        public static class Error {

        }
    }
}