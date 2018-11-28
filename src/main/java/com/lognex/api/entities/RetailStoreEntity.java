package com.lognex.api.entities;

import com.lognex.api.entities.agents.AgentEntity;
import com.lognex.api.entities.agents.EmployeeEntity;
import com.lognex.api.entities.agents.OrganizationEntity;
import com.lognex.api.responses.ListEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RetailStoreEntity extends MetaEntity implements Fetchable {
    private Boolean shared;
    private Boolean controlCashierChoice;
    private String externalCode;
    private String frNumber;
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
    private ListEntity<CashierEntity> cashiers;
    private Boolean active;
    private StoreEntity store;
    private Boolean discountEnable;
    private Environment environment;
    private Boolean sellReserves;
    private Boolean authTokenAttached;
    private Boolean egaisEnabled;
    private OrganizationEntity organization;
    private Integer discountMaxPercent;
    private LocalDateTime updated;
    private String description;
    private String address;
    private Boolean onlyInStock;
    private AgentEntity acquire;
    private StateEntity orderToState;
    private ListEntity<StateEntity> customerOrderStates;

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
            private String name;
            private String vendor;
            private String version;
        }

        @Getter
        @Setter
        @NoArgsConstructor
        public static class ChequePrinter {
            private String vendor;
            private String name;
            private String serial;
            private String fiscalDataVersion;
            private Driver driver;
            private FiscalMemory fiscalMemory;
            private String firmwareVersion;

            @Getter
            @Setter
            @NoArgsConstructor
            public class Driver {
                private String name;
                private String version;
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class State {
        private Sync sync;
        private LocalDateTime lastCheckMoment;
        private FiscalMemory fiscalMemory;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Sync {
            private String message;
            private String lastAttempMoment;
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class FiscalMemory {
        private Error error;
        private Integer notSendDocCount;
        private LocalDateTime notSendFirstDocMoment;

        @Getter
        @Setter
        @NoArgsConstructor
        public static class Error {
            private String code;
            private String message;
        }
    }
}